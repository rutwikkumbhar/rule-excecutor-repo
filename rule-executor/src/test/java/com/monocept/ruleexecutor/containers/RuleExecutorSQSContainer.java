package com.monocept.ruleexecutor.containers;

import org.testcontainers.containers.localstack.LocalStackContainer;

public class RuleExecutorSQSContainer {

    private static LocalStackContainer localStackContainer;

    public static LocalStackContainer getInstance() {
        if (localStackContainer == null || !localStackContainer.isRunning()) {
            localStackContainer = new LocalStackContainer()
                    .withServices(LocalStackContainer.Service.SQS);
            localStackContainer.start();
        }
        start();
        return localStackContainer;
    }

    public static void start() {
        System.setProperty("SQS_ENDPOINT", localStackContainer.getEndpoint().toString());
        System.setProperty("AWS_ACCESS_KEY", localStackContainer.getAccessKey());
        System.setProperty("AWS_SECRET_KEY", localStackContainer.getSecretKey());
        System.setProperty("AWS_REGION", localStackContainer.getRegion());
    }

    public static void stop() {
        localStackContainer.stop();
    }
}
