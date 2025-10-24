package com.darcsoftware.eventsapi.event.dto;

public record EventHostLinkCreate(
        long partyId,
        String role,
        Integer sortOrder
) {}