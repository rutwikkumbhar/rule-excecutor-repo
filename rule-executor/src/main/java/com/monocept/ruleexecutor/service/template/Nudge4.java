package com.monocept.ruleexecutor.service.template;

import com.monocept.ruleexecutor.model.NudgesEvent;
import com.monocept.ruleexecutor.model.Rules;
import com.monocept.ruleexecutor.model.Templates;
import com.monocept.ruleexecutor.model.enums.TemplateNames;
import com.monocept.ruleexecutor.repository.LeadsRepository;
import com.monocept.ruleexecutor.sqs.RuleExecutorPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Slf4j
public class Nudge4 implements TemplateRunnerService{
    @Autowired
    LeadsRepository leadsRepository;

    @Autowired
    RuleExecutorPublisher ruleExecutorPublisher;

    @Value("${spring.cloud.queues.rule-executor-queue}")
    String inputQueue;

    private Stream<NudgesEvent> prepareNudgeEventTemplate(Integer count, Rules rules, Templates templates, Map<String, Object> v){
        if (count == 0) {
            log.debug("this nudge {} belongs to template {}", rules.getType(), templates);
            return Stream.of(NudgesEvent.builder()
                    .nudgeId(rules.getType())
                    .userId(v.get("userId").toString())
                    .sender(rules.getSender())
                    .receiver(rules.getReceiver())
                    .cta(rules.getCta())
                    .landingPage(rules.getLandingPage())
                    .deviceId(v.get("deviceId").toString())
                    .template(templates)
                    .count(Integer.parseInt(v.get("count").toString()))
                    .build());
        } else {
            log.debug("No count was return from leads");
            return Stream.empty();
        }
    }


    @Override
    public Stream<NudgesEvent> prepareEvent(Rules rules) {
        List<Map<String, Object>> res = leadsRepository.nudge4();
        return res.stream().flatMap(v-> {
            int count = Integer.parseInt(v.get("count").toString());
            return rules.getTemplates().stream().flatMap(template -> prepareNudgeEventTemplate(count, rules,template, v));
        });
    }

    @Override
    public void sendEvent(Stream<NudgesEvent> nudgesEvent) {
        nudgesEvent.forEach(v-> ruleExecutorPublisher.sendMessage(inputQueue, v));
    }
}
