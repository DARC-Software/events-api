package com.darcsoftware.eventsapi.room.dto;

import java.time.Instant;

public record RoomResponse(
        Long id,
        Long venueId,
        String name,
        Instant createdAt,
        Instant updatedAt
) {}