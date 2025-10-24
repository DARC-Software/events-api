// room/RoomController.java
package com.darcsoftware.eventsapi.room;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.room.dto.RoomCreateRequest;
import com.darcsoftware.eventsapi.room.dto.RoomResponse;
import com.darcsoftware.eventsapi.room.dto.RoomUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@Tag(name = "Room", description = "Endpoints for managing rooms of venues")
public class RoomController {
    private final RoomService svc;

    public RoomController(RoomService svc) { this.svc = svc; }

    @GetMapping("/api/rooms/{id}")
    @Operation(operationId = "Room_get")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class)))
    })
    public RoomResponse get(@PathVariable long id) { return svc.get(id); }

    @GetMapping("/api/rooms/venue/{venueId}")
    @Operation(operationId = "Room_listByVenue")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoomResponse.class))))
    })
    public List<RoomResponse> listByVenue(@PathVariable long venueId) { return svc.listByVenue(venueId); }

    @PostMapping(value = "/api/rooms", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "Room_create")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    headers = {
                            @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource",
                                    schema = @Schema(type = "string", format = "uri"))
                    },
                    content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = RoomResponse.class)))
    })
    public RoomResponse create(@RequestBody RoomCreateRequest req) { return svc.create(req); }

    @PatchMapping(value = "/api/rooms/{id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(operationId = "Room_rename")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class)))
    })
    public RoomResponse rename(@PathVariable long id, @RequestBody RoomUpdateRequest req) { return svc.rename(id, req); }

    @DeleteMapping("/api/rooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "Room_delete")
    @ApiResponses({ @ApiResponse(responseCode = "204") })
    public void delete(@PathVariable long id) { svc.delete(id); }
}