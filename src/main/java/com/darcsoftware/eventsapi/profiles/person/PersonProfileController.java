package com.darcsoftware.eventsapi.profiles.person;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.party.PartyService;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileGetResponse;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileListRow;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileUpsertRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/profiles/persons", produces = APPLICATION_JSON_VALUE)
@Tag(name = "PersonProfile", description = "Endpoints for managing person profiles")
public class PersonProfileController {
    private final PersonProfileService personProfileService;
    private final PartyService partyService;

    public PersonProfileController(PersonProfileService personProfileService, PartyService partyService) {
        this.personProfileService = personProfileService;
        this.partyService = partyService;
    }

    // Profiles (people) list for admin page
    @GetMapping
    @Operation(operationId = "PersonProfile_list")
    public PageResponse<PersonProfileListRow> listPeople(@RequestParam(required = false) String q,
                                                         @RequestParam(defaultValue = "50") int limit,
                                                         @RequestParam(defaultValue = "0") int offset) {
        return partyService.listPeople(q, limit, offset);
    }

    @GetMapping("/{partyId}")
    @Operation(operationId = "PersonProfile_get")
    public ResponseEntity<PersonProfileGetResponse> get(@PathVariable long partyId) {
        return ResponseEntity.ok(personProfileService.get(partyId));
    }

    @PatchMapping(value = "/{partyId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(operationId = "PersonProfile_update")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonProfileGetResponse.class)))
    })
    public ResponseEntity<PersonProfileGetResponse> update(@PathVariable long partyId, @RequestBody PersonProfileUpsertRequest req) {
        return ResponseEntity.ok(personProfileService.upsertAndGet(partyId, req));
    }

    @DeleteMapping("/{partyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "PersonProfile_delete")
    public void delete(@PathVariable long partyId) { personProfileService.delete(partyId); }
}