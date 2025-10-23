package com.darcsoftware.eventsapi.event.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        Long hostId,
        Long venueId,
        Long roomId,
        String title,
        String description,
        String timezone,
        Integer offsetMinutes,
        LocalDateTime startTimeLocal,
        LocalDateTime endTimeLocal,
        Instant startTimeUtc,
        Instant endTimeUtc
) {}
