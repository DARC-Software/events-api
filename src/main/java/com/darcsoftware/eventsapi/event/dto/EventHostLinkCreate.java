package com.darcsoftware.eventsapi.event.dto;

public record EventHostLinkCreate(
        Long partyId,
        String role,        // optional
        Integer sortOrder   // optional
) {}