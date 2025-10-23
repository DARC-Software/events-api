package com.darcsoftware.eventsapi.party.dto;

import java.util.List;

public record GroupMemberListResponse(
        List<GroupMemberResponse> items,
        Integer limit,
        Integer offset,
        Long total
) {}
