package com.monocept.ruleexecutor.service;

import com.monocept.ruleexecutor.model.Rules;
import com.monocept.ruleexecutor.model.enums.NudgesNames;
import com.monocept.ruleexecutor.service.template.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class PrepareEventsService {

    @Autowired
    private RuleEngineService ruleEngineService;

    @Autowired
    private Nudge1 nudge1;

    @Autowired
    private Nudge2 nudge2;

    @Autowired
    private Nudge3 nudge3;

    @Autowired
    private Nudge4 nudge4;

    @Autowired
    private Nudge5 nudge5;

    public void prepareEvents(String nudgeId) {
        Optional<Rules> rules = ruleEngineService.getRulesById(nudgeId);
        rules.map(rule-> {
                switch (NudgesNames.valueOf(rule.getType())) {
                    case transaction_1:
                        log.debug("Executing rule for nudgeId {}", rule.getType());
                        nudge1.sendEvent(nudge1.prepareEvent(rule));
                    case transaction_2:
                        log.debug("Executing rule for nudgeId {}", rule.getType());
                        nudge2.sendEvent(nudge2.prepareEvent(rule));
                    case transaction_3:
                        log.debug("Executing rule for nudgeId {}", rule.getType());
                        nudge3.sendEvent(nudge3.prepareEvent(rule));
                    case transaction_4:
                        log.debug("Executing rule for nudgeId {}", rule.getType());
                        nudge4.sendEvent(nudge4.prepareEvent(rule));
                    case transaction_5:
                        log.debug("Executing rule for nudgeId {}", rule.getType());
                        nudge5.sendEvent(nudge5.prepareEvent(rule));
                }
                return null;
        });
    }
}
