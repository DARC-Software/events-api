// party/dto/GroupMemberResponse.java
package com.darcsoftware.eventsapi.party.dto;

public record GroupMemberResponse(
        long groupId,
        long memberPartyId,
        String role,
        int sortOrder,
        String memberDisplayName,
        String memberSlug
) {}