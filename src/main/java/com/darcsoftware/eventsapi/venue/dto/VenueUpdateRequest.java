// venue/dto/VenueUpdateRequest.java
package com.darcsoftware.eventsapi.venue.dto;

public record VenueUpdateRequest(
        String name,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String zipCode
) {}