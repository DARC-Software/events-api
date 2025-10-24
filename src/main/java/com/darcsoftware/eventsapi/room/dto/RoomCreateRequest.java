package com.darcsoftware.eventsapi.room.dto;

public record RoomCreateRequest(
        long venueId,
        String name
) {}