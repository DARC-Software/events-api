// room/RoomService.java
package com.darcsoftware.eventsapi.room;

import com.darcsoftware.eventsapi.room.dto.RoomCreateRequest;
import com.darcsoftware.eventsapi.room.dto.RoomResponse;
import com.darcsoftware.eventsapi.room.dto.RoomUpdateRequest;
import com.darcsoftware.eventsapi.venue.VenueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {
    private final RoomMapper rooms;
    private final VenueService venues;

    public RoomService(RoomMapper rooms, VenueService venues) {
        this.rooms = rooms;
        this.venues = venues;
    }

    public RoomResponse get(long id) {
        return rooms.get(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }

    public List<RoomResponse> listByVenue(long venueId) {
        // ensure venue exists (nice 404)
        venues.get(venueId);
        return rooms.listByVenue(venueId);
    }

    @Transactional
    public RoomResponse create(RoomCreateRequest req) {
        // ensure venue exists
        venues.get(req.venueId());
        rooms.insert(req.venueId(), req.name());
        // no slug; fetch by last row is messy; requery list and pick by name
        return rooms.listByVenue(req.venueId())
                .stream().filter(r -> r.name().equals(req.name()))
                .reduce((a, b) -> b) // last
                .orElseThrow(() -> new IllegalStateException("Failed to create room"));
    }

    @Transactional
    public RoomResponse rename(long id, RoomUpdateRequest req) {
        rooms.rename(id, req.name());
        return get(id);
    }

    @Transactional
    public void delete(long id) {
        int n = rooms.delete(id);
        if (n == 0) throw new IllegalArgumentException("Room not found");
    }
}