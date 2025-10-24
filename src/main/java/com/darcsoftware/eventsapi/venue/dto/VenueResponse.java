// venue/dto/VenueResponse.java
package com.darcsoftware.eventsapi.venue.dto;

import java.time.Instant;

public record VenueResponse(
        long id,
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