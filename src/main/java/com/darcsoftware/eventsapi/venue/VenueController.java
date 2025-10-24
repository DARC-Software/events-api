// venue/VenueController.java
package com.darcsoftware.eventsapi.venue;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.venue.dto.PageResponseVenueResponse;
import com.darcsoftware.eventsapi.venue.dto.VenueCreateRequest;
import com.darcsoftware.eventsapi.venue.dto.VenueResponse;
import com.darcsoftware.eventsapi.venue.dto.VenueUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/venues", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Venue", description = "Endpoints for managing venues")
public class VenueController {
    private final VenueService svc;

    public VenueController(VenueService svc) { this.svc = svc; }

    @GetMapping
    @Operation(operationId = "Venue_list", summary = "List venues")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PageResponseVenueResponse.class)))
    })
    public PageResponse<VenueResponse> list(@RequestParam(defaultValue = "50") int limit,
                                          @RequestParam(defaultValue = "0") int offset) {
        return svc.list(limit, offset);
    }

    @GetMapping("/{id}")
    @Operation(operationId = "Venue_get", summary = "Get a venue")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VenueResponse.class)))
    })
    public VenueResponse get(@PathVariable long id) { return svc.get(id); }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "Venue_create", summary = "Create a venue")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    headers = {
                        @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource",
                                schema = @Schema(type = "string", format = "uri"))
                    },
                    content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = VenueResponse.class)))
    })
    public VenueResponse create(@RequestBody VenueCreateRequest req) { return svc.create(req); }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(operationId = "Venue_update", summary = "Update a venue")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VenueResponse.class)))
    })
    public VenueResponse update(@PathVariable long id, @RequestBody VenueUpdateRequest req) {
        return svc.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "Venue_delete", summary = "Delete a venue")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    public void delete(@PathVariable long id) { svc.delete(id); }
}