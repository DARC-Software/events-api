package com.wildlighttech.barsync_events_api.venue;

import com.wildlighttech.barsync_events_api.venue.Venue;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public Venue getVenueById(@PathVariable Long id) {
        return this.venueService.getVenueById(id);
    }

    @PostMapping()
    public void createVenue(@RequestBody Venue venue) {
        this.venueService.createVenue(venue);
    }

    @PutMapping("/{id}")
    public void updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        venue.setId(id);
        this.venueService.updateVenue(venue);
    }

    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        this.venueService.deleteVenue(id);
    }
}
