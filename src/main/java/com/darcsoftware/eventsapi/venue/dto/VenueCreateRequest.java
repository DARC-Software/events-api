// venue/dto/VenueCreateRequest.java
package com.darcsoftware.eventsapi.venue.dto;

public record VenueCreateRequest(
        String name,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String zipCode
) {}