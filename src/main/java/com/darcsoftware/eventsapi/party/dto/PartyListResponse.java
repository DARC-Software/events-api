package com.darcsoftware.eventsapi.party.dto;

import java.util.List;

public record PartyListResponse(
        List<PartyLookupItem> items,
        Integer limit,
        Integer offset,
        Long total
) {}
