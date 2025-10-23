package com.darcsoftware.eventsapi.venue;

import com.darcsoftware.eventsapi.venue.dto.VenueCreateRequest;
import com.darcsoftware.eventsapi.venue.dto.VenueResponse;
import com.darcsoftware.eventsapi.venue.dto.VenueUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class VenueService {
    private final VenueMapper venues;

    public VenueService(VenueMapper venues) {
        this.venues = venues;
    }

    public VenueResponse get(Long id) {
        return venues.findById(id).orElseThrow(() -> new IllegalArgumentException("Venue not found: " + id));
    }

    public List<VenueResponse> list(int limit, int offset) {
        return venues.list(limit, offset);
    }



    @Transactional
    public VenueResponse update(Long id, VenueUpdateRequest req) {
        venues.update(id, req);
        return get(id);
    }
    @Transactional
    public VenueResponse create(VenueCreateRequest req) {
        final String slug = normalizeSlug(req.getSlug() == null || req.getSlug().isBlank() ? req.getName() : req.getSlug(), req.getCity());
        var existing = venues.findBySlug(slug);
        if (existing.isPresent()) return existing.get();

        req.setSlug(slug);
        venues.insert(req);
        return venues.findBySlug(slug).orElseThrow(() -> new IllegalStateException("Failed to create venue"));
    }
    public void delete(Long id) {
        venues.delete(id);
    }

    private String normalizeSlug(String name, String city) {
        // Combine name and city into one string
        String combined = (name == null ? "" : name.trim()) + " " + (city == null ? "" : city.trim());

        // Normalize to lowercase, remove accents, and replace spaces/special chars with hyphens
        combined = java.text.Normalizer.normalize(combined, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // remove diacritics (e.g. é → e)
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-") // non-alphanumeric to "-"
                .replaceAll("(^-+|-+$)", "");  // trim leading/trailing "-"

        return combined;
    }
}
