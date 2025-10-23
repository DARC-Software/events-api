package com.darcsoftware.eventsapi.party;

import com.darcsoftware.eventsapi.party.dto.*;
import com.darcsoftware.eventsapi.profiles.dto.PartyWithPersonProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService service;

    public PartyController(PartyService service) {
        this.service = service;
    }

    // --- existing endpoints for persons & groups and party list ---
    @PostMapping("/persons")
    public ResponseEntity<PartyWithPersonProfileResponse> createPerson(@RequestBody CreatePersonWithPartyRequest req) {
        return ResponseEntity.ok(service.createPersonWithParty(req));
    }

    @PostMapping("/groups")
    public ResponseEntity<PartyWithGroupProfileResponse> createGroup(@RequestBody CreateGroupWithPartyRequest req) {
        return ResponseEntity.ok(service.createGroupWithParty(req));
    }

    @GetMapping
    public ResponseEntity<PartyListResponse> list(
            @RequestParam(required = false) PartyType type,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "50") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        return ResponseEntity.ok(service.list(new PartyListQuery(
                new com.darcsoftware.eventsapi.common.PageRequest(limit, offset), type, q)));
    }

    // --- NEW: group members ---
    @GetMapping("/groups/{groupId}/members")
    public ResponseEntity<GroupMemberListResponse> listMembers(
            @PathVariable Long groupId,
            @RequestParam(defaultValue = "50") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        return ResponseEntity.ok(service.listMembers(groupId, limit, offset));
    }

    @PostMapping("/groups/{groupId}/members")
    public ResponseEntity<GroupMemberResponse> addMember(
            @PathVariable Long groupId,
            @RequestBody GroupMemberCreateRequest req
    ) {
        return ResponseEntity.ok(service.addMember(groupId, req));
    }

    @PatchMapping("/groups/{groupId}/members/{memberPartyId}")
    public ResponseEntity<GroupMemberResponse> updateMember(
            @PathVariable Long groupId,
            @PathVariable Long memberPartyId,
            @RequestBody GroupMemberUpdateRequest req
    ) {
        return ResponseEntity.ok(service.updateMember(groupId, memberPartyId, req));
    }

    @DeleteMapping("/groups/{groupId}/members/{memberPartyId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long groupId,
            @PathVariable Long memberPartyId
    ) {
        service.removeMember(groupId, memberPartyId);
        return ResponseEntity.noContent().build();
    }
}