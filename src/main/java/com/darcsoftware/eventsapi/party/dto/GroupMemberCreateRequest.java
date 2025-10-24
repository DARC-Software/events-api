// party/dto/GroupMemberCreateRequest.java
package com.darcsoftware.eventsapi.party.dto;

public record GroupMemberCreateRequest(
        long memberPartyId,
        String role,
        Integer sortOrder
) {}