package com.darcsoftware.eventsapi.common;

import java.util.List;

public record PageResponse<T>(List<T> items, Integer limit, Integer offset, Long total) {}
