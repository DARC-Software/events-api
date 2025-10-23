package com.darcsoftware.eventsapi.venue.dto;

import java.time.Instant;

public record VenueResponse(
        Long id,
        String name,
        String slug,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String zipCode,
        Instant createdAt,
        Instant updatedAt
) {}