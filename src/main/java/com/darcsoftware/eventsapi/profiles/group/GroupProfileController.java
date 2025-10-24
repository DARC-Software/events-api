package com.darcsoftware.eventsapi.profiles.group;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.party.PartyService;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileGetResponse;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileListRow;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileUpsertRequest;
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
@RequestMapping(value = "/api/profiles/groups", produces = APPLICATION_JSON_VALUE)
@Tag(name = "GroupProfile", description = "Endpoints for managing group profiles")
public class GroupProfileController {
    private final GroupProfileService groupProfileService;
    private final PartyService partyService;

    public GroupProfileController(GroupProfileService groupProfileService, PartyService partyService) {
        this.groupProfileService = groupProfileService;
        this.partyService = partyService;
    }

    // Profiles (groups) list for admin page
    @GetMapping
    @Operation(operationId = "GroupProfile_list")
    public PageResponse<GroupProfileListRow> listGroups(@RequestParam(required = false) String q,
                                                        @RequestParam(defaultValue = "50") int limit,
                                                        @RequestParam(defaultValue = "0") int offset) {
        return partyService.listGroups(q, limit, offset);
    }

    @GetMapping("/{partyId}")
    @Operation(operationId = "GroupProfile_get")
    public ResponseEntity<GroupProfileGetResponse> get(@PathVariable long partyId) {
        return ResponseEntity.ok(groupProfileService.get(partyId));
    }

    @PatchMapping(value = "/{partyId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(operationId = "GroupProfile_update")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GroupProfileGetResponse.class)))
    })
    public ResponseEntity<GroupProfileGetResponse> update(@PathVariable long partyId, @RequestBody GroupProfileUpsertRequest req) {
        return ResponseEntity.ok(groupProfileService.upsertAndGet(partyId, req));
    }

    @DeleteMapping("/{partyId}")
    @Operation(operationId = "GroupProfile_delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long partyId) { groupProfileService.delete(partyId); }
}