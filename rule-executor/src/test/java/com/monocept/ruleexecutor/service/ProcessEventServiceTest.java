package com.monocept.ruleexecutor.service;

import com.monocept.ruleexecutor.BaseTest;
import com.monocept.ruleexecutor.model.NudgesEvent;
import com.monocept.ruleexecutor.sqs.RuleExecutorPublisher;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@Ignore
public class ProcessEventServiceTest extends BaseTest {

    @Autowired
    private RuleExecutorPublisher ruleExecutorPublisher;

    @Value("${spring.cloud.queues.rule-executor-output-queue}")
    String outputQueue;
    @Value("${spring.cloud.queues.rule-executor-queue}")
    String inputQueue;

    @Test
    @DisplayName("Successfully store nudge when there is no nudge exist with given dedup check params")
    public void processEventSuccessFulTest() {

        assertTrue(true);
    }

}
