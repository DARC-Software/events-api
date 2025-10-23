package com.darcsoftware.eventsapi.venue.dto;

import lombok.Data;

@Data
public class VenueCreateRequest {
        String id;
        String name;
        String slug;          // optional; if null we slugify name
        String addressLine1;
        String addressLine2;  // optional
        String city;
        String state;         // 2-letter
        String zipCode;
}