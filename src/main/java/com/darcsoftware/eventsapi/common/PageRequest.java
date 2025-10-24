package com.darcsoftware.eventsapi.common;

public record PageRequest(int limit, int offset) {
    public static PageRequest of(Integer limit, Integer offset) {
        return new PageRequest(limit == null ? 50 : limit, offset == null ? 0 : offset);
    }
}