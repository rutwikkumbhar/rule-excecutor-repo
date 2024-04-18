package com.monocept.ruleexecutor.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monocept.ruleexecutor.model.NudgesEvent;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RuleExecutorPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RuleExecutorPublisher.class);

    private final SqsTemplate sqsTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RuleExecutorPublisher(SqsTemplate sqsTemplate, ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendMessage(String queue, NudgesEvent payload) {
        logger.debug("Sending message to queue with payload {}", payload);
        sqsTemplate.send(to -> {
            try {
                to.queue(queue).payload(objectMapper.writeValueAsString(payload));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        logger.debug("Message sent with payload {}", payload);
    }
}
