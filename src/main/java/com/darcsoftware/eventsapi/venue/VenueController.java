package com.darcsoftware.eventsapi.venue;

import com.darcsoftware.eventsapi.venue.dto.VenueCreateRequest;
import com.darcsoftware.eventsapi.venue.dto.VenueResponse;
import com.darcsoftware.eventsapi.venue.dto.VenueUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<VenueResponse>> list(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(venueService.list(limit, offset));
    }

    @PostMapping
    public ResponseEntity<VenueResponse> create(@RequestBody VenueCreateRequest req) {
        return ResponseEntity.ok(venueService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueResponse> update(@PathVariable Long id, VenueUpdateRequest req) {
        return ResponseEntity.ok(venueService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        venueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
