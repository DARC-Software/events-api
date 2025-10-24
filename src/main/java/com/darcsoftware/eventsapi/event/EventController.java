// event/EventController.java
package com.darcsoftware.eventsapi.event;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.event.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Event", description = "Endpoints for managing events")
public class EventController {
    private final EventService svc;

    public EventController(EventService svc) { this.svc = svc; }

    @PostMapping(value = "/events", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "Event_create")
    public EventSummary create(@RequestBody EventCreateRequest req) { return svc.create(req); }

    @GetMapping("/events/{id}")
    @Operation(operationId = "Event_get")
    public EventSummary get(@PathVariable long id) { return svc.get(id); }

    @PatchMapping(value = "/events/{id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(operationId = "Event_update")
    public EventSummary update(@PathVariable long id, @RequestBody EventUpdateRequest req) { return svc.update(id, req); }

    @DeleteMapping("/events/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "Event_delete")
    public void delete(@PathVariable long id) { svc.delete(id); }

    @GetMapping("/venues/{venueId}/events")
    @Operation(operationId = "Event_listByVenue")
    public PageResponse<EventSummary> listByVenue(@PathVariable long venueId,
                                                  @RequestParam(defaultValue = "50") int limit,
                                                  @RequestParam(defaultValue = "0") int offset) {
        return svc.listByVenue(venueId, limit, offset);
    }

    @GetMapping("/hosts/{hostId}/events")
    @Operation(operationId = "Event_listByHost")
    public PageResponse<EventSummary> listByHost(@PathVariable long hostId,
                                                 @RequestParam(defaultValue = "50") int limit,
                                                 @RequestParam(defaultValue = "0") int offset) {
        return svc.listByHost(hostId, limit, offset);
    }
}