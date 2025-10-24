package com.darcsoftware.eventsapi.event.dto;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * All fields optional; only provided ones will be applied.
 * Service recalculates UTC + offset_minutes from local+timezone when any of those change.
 */
public record EventUpdateRequest(
        Long venueId,
        Long roomId,
        String title,
        String description,
        String backgroundUrl,
        OffsetDateTime startTimeLocal,
        OffsetDateTime endTimeLocal,
        String timezone,
        List<EventHostLinkCreate> hostsReplace
) {}