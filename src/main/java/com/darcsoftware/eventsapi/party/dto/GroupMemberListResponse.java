// party/dto/GroupMemberListResponse.java
package com.darcsoftware.eventsapi.party.dto;

import com.darcsoftware.eventsapi.common.PageResponse;

public record GroupMemberListResponse(PageResponse<GroupMemberResponse> page) {}