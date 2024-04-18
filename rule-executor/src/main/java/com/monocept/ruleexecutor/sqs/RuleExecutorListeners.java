package com.monocept.ruleexecutor.sqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monocept.ruleexecutor.model.NudgesEvent;
import com.monocept.ruleexecutor.service.ProcessEventService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class RuleExecutorListeners {

    private final SqsTemplate sqsTemplate;

    private final ProcessEventService processEventService;

    @Autowired
    public RuleExecutorListeners(ProcessEventService processEventService, SqsTemplate sqsTemplate) {
        this.processEventService = processEventService;
        this.sqsTemplate = sqsTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(RuleExecutorListeners.class);

    @SqsListener(value = "${spring.cloud.queues.rule-executor-queue}", id = "rule-executor-container")
    public void ruleExecutorListener(Message<String> message) {
        logger.info("Message received: {}", message);
        try {
            processEventService.processEvent(new ObjectMapper().readValue(message.getPayload(), NudgesEvent.class));
        } catch (Exception ex) {
            logger.error("Exception in processing event ", ex);
        }
        Acknowledgement.acknowledge(message);
        logger.info("Message processed successfully: {}", message);
    }
}
