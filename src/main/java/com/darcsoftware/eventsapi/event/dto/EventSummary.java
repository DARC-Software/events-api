package com.darcsoftware.eventsapi.event.dto;

import java.time.OffsetDateTime;
import java.time.Instant;

public record EventSummary(
        long id,
        String title,
        String description,
        long venueId,
        String venueName,
        Long roomId,
        String roomName,
        String timezone,
        int offsetMinutes,
        OffsetDateTime startTimeLocal,
        OffsetDateTime endTimeLocal,
        Instant startTimeUtc,
        Instant endTimeUtc,
        String backgroundUrl
) {}