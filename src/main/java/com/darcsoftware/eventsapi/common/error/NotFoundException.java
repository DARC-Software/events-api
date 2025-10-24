package com.darcsoftware.eventsapi.common.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) { super(msg); }
}