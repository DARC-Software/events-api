package com.darcsoftware.eventsapi.event.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record EventCreateRequest(
        Long parentEventId,
        long venueId,
        Long roomId,
        String title,
        String description,
        String backgroundUrl,
        OffsetDateTime startTimeLocal,
        OffsetDateTime endTimeLocal,
        String timezone,
        List<EventHostLinkCreate> hosts
) {}