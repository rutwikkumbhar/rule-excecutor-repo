package com.monocept.ruleexecutor.error;

public class NudgesReachedThrottleLimitException extends RuntimeException {
    public NudgesReachedThrottleLimitException(String message) {
        super(message);
    }
}
