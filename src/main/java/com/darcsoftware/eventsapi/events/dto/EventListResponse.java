package com.darcsoftware.eventsapi.events.dto;

import java.util.List;

public record EventListResponse(
        List<EventSummary> items,
        Integer limit,
        Integer offset,
        Long total
) {}
