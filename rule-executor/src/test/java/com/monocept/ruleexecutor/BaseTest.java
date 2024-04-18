package com.monocept.ruleexecutor;

import com.monocept.ruleexecutor.containers.RuleExecutorPostgresContainer;
import com.monocept.ruleexecutor.containers.RuleExecutorRuleEngineContainer;
import com.monocept.ruleexecutor.containers.RuleExecutorSQSContainer;
import org.junit.ClassRule;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;

@TestPropertySource(locations = "classpath:application-test.yaml")
@ActiveProfiles("test")
public class BaseTest {
    @ClassRule
    public static RuleExecutorPostgresContainer ruleExecutorPostgresqlContainer = RuleExecutorPostgresContainer.getInstance();

    @ClassRule
    public static LocalStackContainer ruleExecutorSQSContainer = RuleExecutorSQSContainer.getInstance();

    @ClassRule
    public static GenericContainer<?> wireMockContainer = RuleExecutorRuleEngineContainer.getInstance();
}
