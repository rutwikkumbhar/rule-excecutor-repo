package com.monocept.ruleexecutor.error;

public class NoSenderFoundException extends RuntimeException{
    public NoSenderFoundException(String message) {
        super(message);
    }
}