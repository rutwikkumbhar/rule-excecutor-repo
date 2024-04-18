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
public class Nudge1 implements TemplateRunnerService {

    @Autowired
    LeadsRepository leadsRepository;

    @Autowired
    RuleExecutorPublisher ruleExecutorPublisher;

    @Value("${spring.cloud.queues.rule-executor-queue}")
    String inputQueue;


    private Stream<NudgesEvent> prepareNudgeEventTemplate1(Integer count, Rules rules, Templates templates, Map<String, Object> v) {
        if (count > 1) {
            templates.setMessageContent(templates.getMessageContent().replaceAll("@@number_of_leads@@", count.toString()));
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

    private Stream<NudgesEvent> prepareNudgeEventTemplate2(Integer count, Rules rules, Templates templates, Map<String, Object> v) {
        if (count == 1) {
            templates.setMessageContent(templates.getMessageContent().replaceAll("@@customer_name@@", v.get("customername").toString()));
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
        List<Map<String, Object>> res = leadsRepository.nudge1();
        return res.stream().flatMap(v -> {
            int count = Integer.parseInt(v.get("count").toString());
            return rules.getTemplates().stream().flatMap(template -> {
                Stream<NudgesEvent> resData = null;
                switch (TemplateNames.valueOf(template.getName())) {
                    case TEMPLATE_1:
                        resData = prepareNudgeEventTemplate1(count, rules, template, v);
                        break;
                    case TEMPLATE_2:
                        resData = prepareNudgeEventTemplate2(count, rules, template, v);
                        break;
                    case TEMPLATE_3:
                        resData = prepareNudgeEventTemplate1(count, rules, template, v);
                        break;
                    case TEMPLATE_4:
                        resData = prepareNudgeEventTemplate2(count, rules, template, v);
                        break;
                }
                return resData;
            });

        });
    }

    @Override
    public void sendEvent(Stream<NudgesEvent> nudgesEvent) {
        nudgesEvent.forEach(v -> ruleExecutorPublisher.sendMessage(inputQueue, v));
    }
}
