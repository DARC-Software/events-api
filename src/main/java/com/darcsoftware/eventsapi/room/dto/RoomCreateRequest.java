package com.darcsoftware.eventsapi.room.dto;

public record RoomCreateRequest(
        Long venueId,
        String name
) {}