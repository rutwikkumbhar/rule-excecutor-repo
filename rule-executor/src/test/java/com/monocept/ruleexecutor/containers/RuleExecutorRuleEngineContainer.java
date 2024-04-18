package com.monocept.ruleexecutor.containers;

import com.monocept.ruleexecutor.TestUtils;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class RuleExecutorRuleEngineContainer {

    @Container
    private static final GenericContainer<?> wireMockContainer = new GenericContainer<>("rodolpheche/wiremock")
            .withExposedPorts(8080)
            .withFileSystemBind("/src/test/resources/wiremock/mappings", "/home/wiremock", BindMode.READ_WRITE);

    public static GenericContainer<?> getInstance() {
        wireMockContainer.start();
        System.setProperty("RULE_ENGINE_URL", TestUtils.getWireMockURL(wireMockContainer));
        return wireMockContainer;
    }

    public void stop() {
        wireMockContainer.stop();
    }
}
