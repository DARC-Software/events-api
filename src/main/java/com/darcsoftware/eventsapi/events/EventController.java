package com.darcsoftware.eventsapi.events;

import com.darcsoftware.eventsapi.events.dto.EventListResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/hosts")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{partyId}/events")
    public EventListResponse getByHost(
            @PathVariable long partyId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fromUtc,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant toUtc,
            @RequestParam(required = false, defaultValue = "false") boolean includePast,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false, defaultValue = "false") boolean includeTotal
    ) {
        return eventService.getByHost(partyId, fromUtc, toUtc, includePast, limit, offset, includeTotal);
    }
}
