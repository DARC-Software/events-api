// party/PartyController.java
package com.darcsoftware.eventsapi.party;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.party.dto.*;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileListRow;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileListRow;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profile", description = "Endpoints for managing parties")
public class PartyController {

    private final PartyService svc;

    public PartyController(PartyService svc) { this.svc = svc; }

    // Create person/group (party + profile)
    @PostMapping(value = "/parties/persons", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "Profile_createPerson")
    public PartyWithPersonProfileResponse createPerson(@RequestBody CreatePersonWithPartyRequest req) {
        return svc.createPerson(req);
    }

    @PostMapping(value = "/parties/groups", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "Profile_createGroup")
    public PartyWithGroupProfileResponse createGroup(@RequestBody CreateGroupWithPartyRequest req) {
        return svc.createGroup(req);
    }

    // Group members
    @GetMapping("/parties/groups/{groupId}/members")
    @Operation(operationId = "GroupMembership_list")
    public PageResponse<GroupMemberResponse> listMembers(@PathVariable long groupId,
                                                         @RequestParam(defaultValue = "50") int limit,
                                                         @RequestParam(defaultValue = "0") int offset) {
        return svc.listMembers(groupId, limit, offset);
    }

    @PostMapping(value = "/parties/groups/{groupId}/members", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "GroupMembership_add")
    public GroupMemberResponse addMember(@PathVariable long groupId,
                                         @RequestBody GroupMemberCreateRequest req) {
        return svc.addMember(groupId, req);
    }

    @PatchMapping(value = "/parties/groups/{groupId}/members/{memberPartyId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(operationId = "GroupMembership_update")
    public GroupMemberResponse updateMember(@PathVariable long groupId,
                                            @PathVariable long memberPartyId,
                                            @RequestBody GroupMemberUpdateRequest req) {
        return svc.updateMember(groupId, memberPartyId, req);
    }

    @DeleteMapping("/parties/groups/{groupId}/members/{memberPartyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "GroupMembership_delete")
    public void deleteMember(@PathVariable long groupId, @PathVariable long memberPartyId) {
        svc.deleteMember(groupId, memberPartyId);
    }
}