package com.darcsoftware.eventsapi.event.dto;

import java.util.List;

public record EventListResponse(
        List<EventSummary> items,
        Integer limit,
        Integer offset,
        Long total
) {}