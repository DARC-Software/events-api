package com.wildlighttech.barsync_events_api.venue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/venues")
public class VenueController {
    @GetMapping("/{venueId}")
    public Venue getVenueById(@PathVariable Long venueId) {
        return new Venue(venueId, "Name 1", "Address 1", "Phone Number 1");
    }
}
