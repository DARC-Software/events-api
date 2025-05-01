package com.wildlighttech.barsync_events_api.event;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<Event> getEvents() { return this.eventService.getEvents(); }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long Id) { return this.eventService.getEventById(Id); }

    @GetMapping("/{venueId}")
    public Event getEventByVenueId(@PathVariable Long venueId) { return this.eventService.getEventByVenueId(venueId);}

    @PostMapping
    public void createEvent(@RequestBody Event event) { this.eventService.createEvent(event); }

    @PutMapping("/{id}")
    public void updateEvent(@PathVariable Long id, @RequestBody Event event) {
        event.setId(id);
        this.eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) { this.eventService.deleteEvent(id); }

}
