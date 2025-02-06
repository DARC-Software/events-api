package com.wildlighttech.barsync_events_api.venue;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venues")
@AllArgsConstructor
public class VenueController {
    private final VenueService venueService;

    @GetMapping
    public List<Venue> getVenues() {
        return this.venueService.getVenues();
    }

    @GetMapping("/{venueId}")
    public Venue getVenueById(@PathVariable long venueId) {
        return this.venueService.getVenueById(venueId);
    }

    @PostMapping()
    public void createVenue(@RequestBody Venue venue) {
        this.venueService.createVenue(venue);
    }

    @PutMapping("/{venueId}")
    public void updateVenue(@RequestBody Venue venue, @PathVariable long venueId) {
        this.venueService.updateVenue(venue, venueId);
    }

    @DeleteMapping("/{venueId}")
    public void deleteVenue(@PathVariable long venueId) {
        this.venueService.deleteVenue(venueId);
    }
}
