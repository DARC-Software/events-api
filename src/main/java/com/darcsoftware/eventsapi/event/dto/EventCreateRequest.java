package com.darcsoftware.eventsapi.event.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EventCreateRequest(
        Long parentEventId,     // optional
        Long venueId,
        Long roomId,            // optional but must belong to venue if provided
        String title,
        String description,     // optional
        String backgroundUrl,   // optional
        LocalDateTime startTimeLocal,
        LocalDateTime endTimeLocal,
        String timezone,        // IANA, e.g. "America/New_York"
        List<EventHostLinkCreate> hosts  // optional
) {}