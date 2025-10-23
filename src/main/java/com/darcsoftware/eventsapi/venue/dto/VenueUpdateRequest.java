package com.darcsoftware.eventsapi.venue.dto;

public record VenueUpdateRequest(
        String name,
        String slug,          // optional
        String addressLine1,
        String addressLine2,  // optional
        String city,
        String state,
        String zipCode
) {}