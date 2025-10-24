package com.darcsoftware.eventsapi.room.dto;

import java.time.Instant;

public record RoomResponse(
        long id,
        long venueId,
        String name,
        Instant createdAt,
        Instant updatedAt
) {}