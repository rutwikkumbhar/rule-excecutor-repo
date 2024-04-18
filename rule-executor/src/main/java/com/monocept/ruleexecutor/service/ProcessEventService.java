package com.monocept.ruleexecutor.service;

import com.monocept.ruleexecutor.error.DuplicateNudgesException;
import com.monocept.ruleexecutor.error.NudgesReachedThrottleLimitException;
import com.monocept.ruleexecutor.model.*;
import com.monocept.ruleexecutor.entity.NudgeStorage;
import com.monocept.ruleexecutor.model.enums.ExecutionStatus;
import com.monocept.ruleexecutor.repository.NudgeStorageRepository;
import com.monocept.ruleexecutor.sqs.RuleExecutorPublisher;
import com.monocept.ruleexecutor.util.DateUtil;
import com.monocept.ruleexecutor.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProcessEventService {

    private final NudgeStorageRepository nudgeStorageRepository;

    private final RuleEngineService ruleEngineService;

    private final RuleExecutorPublisher ruleExecutorPublisher;

    @Value("${spring.cloud.queues.rule-executor-output-queue}")
    String outputQueue;


    @Autowired
    public ProcessEventService(RuleEngineService ruleEngineService, NudgeStorageRepository nudgeStorageRepository, RuleExecutorPublisher ruleExecutorPublisher) {
        this.ruleEngineService = ruleEngineService;
        this.nudgeStorageRepository = nudgeStorageRepository;
        this.ruleExecutorPublisher = ruleExecutorPublisher;
    }


    public NudgeStorage storeNudge(String processId, NudgesEvent nudgesEvent) {
        log.debug("No nudges found with processId {}, creating new NudgeStorage row", processId);
        var nudgeStorage = new NudgeStorage();
        nudgeStorage.setId(UUID.randomUUID());
        nudgeStorage.setCount(1);
        nudgeStorage.setProcessId(processId);
        nudgeStorage.setNudgeId(nudgesEvent.getNudgeId());
        nudgeStorage.setSender(nudgesEvent.getSender());
        nudgeStorage.setReceiver(nudgesEvent.getReceiver());
        nudgeStorage.setTemplates(nudgesEvent.getTemplate().getId());
        nudgeStorage.setStatus(ExecutionStatus.RECEIVED.name());
        nudgeStorage.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        nudgeStorageRepository.save(nudgeStorage);
        return nudgeStorage;
    }

    public NudgeStorage processEventWithProcessId(NudgesEvent nudgesEvent, Rules rule) {
        var processId = nudgesEvent.getId(DateUtil.nudgeCurrentTimestamp());
        int throttleLimit = Optional.ofNullable(rule.getNumberOfNudgesPerDayPerPerson()).map(i -> {
            log.debug("Got number of nudges per day person is {}", i);
            return i;
        }).orElse(0);
        NudgeStorage nudgeStorage = nudgeStorageRepository.getNudgeStorageByProcessId(processId).stream().findFirst().flatMap(nudges -> {
            if (nudges.getCount() >= throttleLimit) {
                log.error("Nudges throttle limit got reached {}", nudges.getCount());
                nudges.setStatus(ExecutionStatus.ERROR.name());
                nudgeStorageRepository.save(nudges);
                throw new NudgesReachedThrottleLimitException("Nudges throttle limit got reached");
            } else {
                log.debug("For nudge {} throttle limit is {}", nudges.getNudgeId(), nudges.getCount());
                nudges.setCount(nudges.getCount() + 1);
                nudges.setStatus(ExecutionStatus.PROCESSING.name());
                nudgeStorageRepository.save(nudges);
                return Optional.of(nudges);
            }
        }).orElse(storeNudge(processId, nudgesEvent));
        log.debug("Running all templates rules started for nudge Id {}", nudgeStorage.getNudgeId());
        ruleExecutorPublisher.sendMessage(outputQueue, nudgesEvent);
        nudgeStorage.setStatus(ExecutionStatus.PROCESSED.name());
        nudgeStorageRepository.save(nudgeStorage);
        log.debug("Running all templates rules completed for nudge Id {}", nudgeStorage.getNudgeId());
        return nudgeStorage;
    }


    public void processEvent(NudgesEvent nudgesEvent) {
        ruleEngineService.getRulesById(nudgesEvent.getNudgeId())
                .map(rule -> Optional.ofNullable(rule.getDDupeCheck()).flatMap(Utils::getDDupeCheck)
                        .flatMap(v -> switch (v) {
                            case NUDGE_ID_SENDER ->
                                    nudgeStorageRepository.getNudgeStorageByNudgeIdSource(nudgesEvent.getNudgeId(), nudgesEvent.getSender()).stream().findFirst();
                            case NUDGE_ID ->
                                    nudgeStorageRepository.getNudgeStorageByNudgeId(nudgesEvent.getNudgeId()).stream().findFirst();
                            case SENDER ->
                                    nudgeStorageRepository.getNudgeStorageBySource(nudgesEvent.getSender()).stream().findFirst();
                        })
                        .map(v -> {
                            log.warn("Got duplicate nudge {}", v);
                            v.setStatus(ExecutionStatus.DUPLICATED.name());
                            nudgeStorageRepository.save(v);
                            throw new DuplicateNudgesException("Got duplicate nudges");
                        })
                        .orElse(processEventWithProcessId(nudgesEvent, rule)));
    }
}
