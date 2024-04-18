package com.monocept.ruleexecutor.error;

public class FetchApiException extends RuntimeException {
    public FetchApiException(String message) {
        super(message);
    }
}