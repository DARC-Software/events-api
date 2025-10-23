package com.darcsoftware.eventsapi.event;

import com.darcsoftware.eventsapi.event.dto.EventCreateRequest;
import com.darcsoftware.eventsapi.event.dto.EventListResponse;
import com.darcsoftware.eventsapi.event.dto.EventSummary;
import com.darcsoftware.eventsapi.event.dto.EventUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventSummary> get(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.get(id));
    }

    @GetMapping("/hosts/{hostId}/events")
    public ResponseEntity<EventListResponse> listByHost(
            @PathVariable Long hostId,
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(eventService.listByHost(hostId, limit, offset));
    }

    @GetMapping("/venues/{venueId}/events")
    public ResponseEntity<EventListResponse> listByVenue(
            @PathVariable Long venueId,
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(eventService.listByVenue(venueId, limit, offset));
    }

    @PostMapping("/events")
    public ResponseEntity<EventSummary> create(@RequestBody EventCreateRequest req) {
        return ResponseEntity.ok(eventService.create(req));
    }

    @PatchMapping("/events/{id}")
    public ResponseEntity<EventSummary> update(@PathVariable Long id, @RequestBody EventUpdateRequest req) {
        return ResponseEntity.ok(eventService.update(id, req));
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
