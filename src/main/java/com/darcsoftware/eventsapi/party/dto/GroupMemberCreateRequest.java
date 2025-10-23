package com.darcsoftware.eventsapi.party.dto;

public record GroupMemberCreateRequest(
        Long memberPartyId,                // must be a PERSON party.id
        String role,
        Integer sortOrder                  // optional; default 0
) {}
