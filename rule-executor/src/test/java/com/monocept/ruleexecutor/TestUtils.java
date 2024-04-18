package com.monocept.ruleexecutor;

import org.testcontainers.containers.GenericContainer;

public class TestUtils {

    public static String getWireMockURL(GenericContainer<?> wireMockContainer) {
        return "http://" + wireMockContainer.getContainerIpAddress() + ":" + wireMockContainer.getMappedPort(8080);
    }
}
