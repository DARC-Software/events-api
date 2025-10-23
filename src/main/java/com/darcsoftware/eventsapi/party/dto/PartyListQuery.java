package com.darcsoftware.eventsapi.party.dto;

import com.darcsoftware.eventsapi.common.PageRequest;

public record PartyListQuery(
        PageRequest page,
        PartyType type,
        String q
) {}
