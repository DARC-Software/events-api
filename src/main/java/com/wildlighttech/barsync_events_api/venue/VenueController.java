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

    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable long id) {
        return this.venueService.getVenueById(id);
    }

    @PostMapping()
    public void createVenue(@RequestBody Venue venue) {
        this.venueService.createVenue(venue);
    }

    @PutMapping("/{id}")
    public void updateVenue(@RequestBody Venue venue, @PathVariable long id) {
        this.venueService.updateVenue(venue, id);
    }

    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable long id) {
        this.venueService.deleteVenue(id);
    }
}
