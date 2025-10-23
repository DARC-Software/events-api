package com.darcsoftware.eventsapi.party.dto;

public record GroupMemberUpdateRequest(
        String role,
        Integer sortOrder
) {}
