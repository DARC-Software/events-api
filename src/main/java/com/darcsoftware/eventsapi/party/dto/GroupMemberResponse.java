package com.darcsoftware.eventsapi.party.dto;

public record GroupMemberResponse(
        Long groupId,
        Long memberPartyId,
        String role,
        Integer sortOrder,
        // denormalized for UI:
        String memberDisplayName,
        String memberSlug
) {}
