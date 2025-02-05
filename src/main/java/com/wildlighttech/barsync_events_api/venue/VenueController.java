package com.wildlighttech.barsync_events_api.venue;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/venues")
public class VenueController {
    @GetMapping("/{venueId}")
    public Venue getVenueById(@PathVariable Long venueId) {
        return new Venue(venueId, "Name 1", "Address 1", "Phone Number 1");
    }

    @PostMapping()
    public void createVenue(@RequestBody Venue venue) {

    }

    @PutMapping("/{venueId}")
    public void updateVenue(@RequestBody Venue venue, @PathVariable Long id) {

    }
}
