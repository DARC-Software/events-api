// event/EventService.java
package com.darcsoftware.eventsapi.event;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.event.dto.*;
import com.darcsoftware.eventsapi.room.RoomService;
import com.darcsoftware.eventsapi.venue.VenueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

@Service
public class EventService {
    private final EventMapper events;
    private final VenueService venues;
    private final RoomService rooms;

    public EventService(EventMapper events, VenueService venues, RoomService rooms) {
        this.events = events;
        this.venues = venues;
        this.rooms = rooms;
    }

    public EventSummary get(long id) {
        return events.get(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    @Transactional
    public EventSummary create(EventCreateRequest req) {
        // Validate venue/room existence
        venues.get(req.venueId());
        if (req.roomId() != null) rooms.get(req.roomId());

        // compute offset and UTC from timezone and local times
        ZoneId zone = ZoneId.of(req.timezone());
        ZonedDateTime startZ = req.startTimeLocal().atZoneSameInstant(zone); // assuming payload is OffsetDateTime; adjust if LocalDateTime
        ZonedDateTime endZ   = req.endTimeLocal().atZoneSameInstant(zone);
        int offsetMinutes = startZ.getOffset().getTotalSeconds() / 60;

        OffsetDateTime startUtc = startZ.withZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();
        OffsetDateTime endUtc   = endZ.withZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();

        events.insert(req.parentEventId(), req.venueId(), req.roomId(), req.title(), req.description(), req.backgroundUrl(),
                req.startTimeLocal(), req.endTimeLocal(), req.timezone(), offsetMinutes, startUtc, endUtc);

        // Simplest: fetch latest by venue (or you could LAST_INSERT_ID via extra select)
        List<EventSummary> recent = events.listByVenue(req.venueId(), 1, 0);
        return recent.isEmpty() ? throwCreate() : recent.get(0);
    }

    @Transactional
    public EventSummary update(long id, EventUpdateRequest req) {
        // optional: validate venue/room if provided
        if (req.venueId() != null) venues.get(req.venueId());
        if (req.roomId() != null) rooms.get(req.roomId());

        Integer offsetMinutes = null;
        OffsetDateTime startUtc = null;
        OffsetDateTime endUtc = null;

        if (req.startTimeLocal() != null && req.timezone() != null) {
            ZoneId zone = ZoneId.of(req.timezone());
            ZonedDateTime startZ = req.startTimeLocal().atZoneSameInstant(zone);
            offsetMinutes = startZ.getOffset().getTotalSeconds() / 60;
            startUtc = startZ.withZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();
        }
        if (req.endTimeLocal() != null && req.timezone() != null) {
            ZoneId zone = ZoneId.of(req.timezone());
            ZonedDateTime endZ = req.endTimeLocal().atZoneSameInstant(zone);
            endUtc = endZ.withZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();
        }

        events.update(id, req.venueId(), req.roomId(), req.title(), req.description(), req.backgroundUrl(),
                req.startTimeLocal(), req.endTimeLocal(), req.timezone(), offsetMinutes, startUtc, endUtc);

        return get(id);
    }

    @Transactional
    public void delete(long id) {
        int n = events.delete(id);
        if (n == 0) throw new IllegalArgumentException("Event not found");
    }

    public PageResponse<EventSummary> listByVenue(long venueId, int limit, int offset) {
        venues.get(venueId);
        return new PageResponse<>(events.listByVenue(venueId, limit, offset), limit, offset, events.countByVenue(venueId));
    }

    public PageResponse<EventSummary> listByHost(long hostId, int limit, int offset) {
        return new PageResponse<>(events.listByHost(hostId, limit, offset), limit, offset, events.countByHost(hostId));
    }

    private EventSummary throwCreate() {
        throw new IllegalStateException("Failed to create event");
    }
}