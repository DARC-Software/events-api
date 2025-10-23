package com.darcsoftware.eventsapi.event.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * All fields optional; only provided ones will be applied.
 * Service recalculates UTC + offset_minutes from local+timezone when any of those change.
 */
public record EventUpdateRequest(
        Long venueId,                // optional
        Long roomId,                 // optional
        String title,                // optional
        String description,          // optional
        String backgroundUrl,        // optional
        LocalDateTime startTimeLocal,// optional
        LocalDateTime endTimeLocal,  // optional
        String timezone,             // optional
        List<EventHostLinkCreate> hostsReplace // optional: simple “replace / upsert” semantics
) {}