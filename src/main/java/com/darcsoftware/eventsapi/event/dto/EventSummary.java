package com.darcsoftware.eventsapi.event.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public record EventSummary(
        Long id,
        String title,
        String description,
        Long venueId,
        String venueName,
        Long roomId,
        String roomName,
        String timezone,
        Integer offsetMinutes,
        LocalDateTime startTimeLocal,
        LocalDateTime endTimeLocal,
        Instant startTimeUtc,
        Instant endTimeUtc,
        String backgroundUrl
) {}