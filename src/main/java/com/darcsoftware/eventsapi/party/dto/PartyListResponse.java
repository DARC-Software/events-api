package com.darcsoftware.eventsapi.party.dto;

import com.darcsoftware.eventsapi.common.PageResponse;

public record PartyListResponse(PageResponse<PartyLookupItem> page) {}