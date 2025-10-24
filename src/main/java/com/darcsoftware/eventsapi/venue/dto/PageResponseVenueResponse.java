package com.darcsoftware.eventsapi.venue.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "PageResponseVenueResponse")
public record PageResponseVenueResponse(
        List<VenueResponse> items,
        int limit,
        int offset,
        long total
) {}
