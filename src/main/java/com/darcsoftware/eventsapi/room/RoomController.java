package com.darcsoftware.eventsapi.room;

import com.darcsoftware.eventsapi.room.dto.RoomCreateRequest;
import com.darcsoftware.eventsapi.room.dto.RoomResponse;
import com.darcsoftware.eventsapi.room.dto.RoomUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.get(id));
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<RoomResponse>> listByVenue(@PathVariable Long venueId) {
        return ResponseEntity.ok(roomService.listByVenue(venueId));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody RoomCreateRequest req) {
        return ResponseEntity.ok(roomService.create(req));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoomResponse> rename(@PathVariable Long id, @RequestBody RoomUpdateRequest req) {
        return ResponseEntity.ok(roomService.rename(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
