package com.wildlighttech.barsync_events_api.venue;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VenueService {
    private final VenueMapper venueMapper;

    public List<Venue> getVenues() {
        return this.venueMapper.getVenues();
    }

    public Venue getVenueById(Long id) {
        return this.venueMapper.getVenueById(id);
    }

    public void createVenue(Venue venue) {
        this.venueMapper.createVenue(venue);
    }

    public void updateVenue(Venue venue) {
        this.venueMapper.updateVenue(venue);
    }

    public void deleteVenue(Long id) {
        this.venueMapper.deleteVenue(id);
    }
}
