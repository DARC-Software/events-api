// venue/VenueService.java
package com.darcsoftware.eventsapi.venue;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.common.SlugService;
import com.darcsoftware.eventsapi.venue.dto.VenueCreateRequest;
import com.darcsoftware.eventsapi.venue.dto.VenueResponse;
import com.darcsoftware.eventsapi.venue.dto.VenueUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VenueService {
    private final VenueMapper venues;
    private final SlugService slugService;

    public VenueService(VenueMapper venues, SlugService slugService) {
        this.venues = venues;
        this.slugService = slugService;
    }

    public VenueResponse get(long id) {
        return venues.get(id).orElseThrow(() -> new IllegalArgumentException("Venue not found"));
    }

    public PageResponse<VenueResponse> list(int limit, int offset) {
        return venues.list(limit, offset);
    }

    @Transactional
    public VenueResponse create(VenueCreateRequest req) {
        // slug = normalize(name + city)
        final String slug = slugService.slugOfVenue(req.name(), req.city());
        ensureSlugUnique(slug, null);

        venues.insert(req.name(), slug, req.addressLine1(), req.addressLine2(), req.city(), req.state(), req.zipCode());
        return venues.findBySlug(slug).orElseThrow(() -> new IllegalStateException("Failed to create venue"));
    }

    @Transactional
    public VenueResponse update(long id, VenueUpdateRequest req) {
        final VenueResponse existing = get(id);
        // On update, recalc slug if name or city changed, else keep the existing
        final String newName = req.name() != null ? req.name() : existing.name();
        final String newCity = req.city() != null ? req.city() : existing.city();

        final String slug = slugService.slugOfVenue(newName, newCity);
        ensureSlugUnique(slug, id);

        venues.update(id, req.name(), slug, req.addressLine1(), req.addressLine2(), req.city(), req.state(), req.zipCode());
        return venues.get(id).orElseThrow(() -> new IllegalStateException("Failed to update venue"));
    }

    @Transactional
    public void delete(long id) {
        // Will fail with FK RESTRICT if there are events using this venue.
        int n = venues.delete(id);
        if (n == 0) throw new IllegalArgumentException("Venue not found");
    }

    private void ensureSlugUnique(String slug, Long excludeId) {
        if (venues.countBySlugExclude(slug, excludeId) > 0) {
            throw new IllegalArgumentException("Slug already exists for another venue");
        }
    }
}