package com.darcsoftware.eventsapi.room;

import com.darcsoftware.eventsapi.room.dto.RoomCreateRequest;
import com.darcsoftware.eventsapi.room.dto.RoomResponse;
import com.darcsoftware.eventsapi.room.dto.RoomUpdateRequest;
import com.darcsoftware.eventsapi.venue.VenueMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {
    private final RoomMapper rooms;
    private final VenueMapper venues;

    public RoomService(RoomMapper rooms, VenueMapper venues) {
        this.rooms = rooms;
        this.venues = venues;
    }

    public RoomResponse get(Long id) {
        return rooms.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found: " + id));
    }

    public List<RoomResponse> listByVenue(Long venueId) {
        venues.findById(venueId).orElseThrow(() -> new IllegalArgumentException("Venue not found: " + venueId));
        return rooms.listByVenue(venueId);
    }

    @Transactional
    public RoomResponse create(RoomCreateRequest req) {
        venues.findById(req.venueId()).orElseThrow(() -> new IllegalArgumentException("Venue not found: " + req.venueId()));
        var dup = rooms.findByVenueAndName(req.venueId(), req.name());
        if (dup.isPresent()) return dup.get();

        rooms.insert(req.venueId(), req.name());
        return rooms.findByVenueAndName(req.venueId(), req.name())
                .orElseThrow(() -> new IllegalStateException("Failed to create room"));
    }

    @Transactional
    public RoomResponse rename(Long id, RoomUpdateRequest req) {
        rooms.rename(id, req.name());
        return get(id);
    }

    @Transactional
    public void delete(Long id) {
        rooms.delete(id);
    }
}
