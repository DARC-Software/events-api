package com.darcsoftware.eventsapi.common;

public record PageRequest(
        Integer limit,
        Integer offset
) {}
