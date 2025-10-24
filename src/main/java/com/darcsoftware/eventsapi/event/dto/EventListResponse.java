package com.darcsoftware.eventsapi.event.dto;

import com.darcsoftware.eventsapi.common.PageResponse;

public record EventListResponse(PageResponse<EventSummary> page) {}