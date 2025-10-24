package com.darcsoftware.eventsapi.party.dto;

public record PartyListQuery(
        PartyType type,
        String q,
        int limit,
        int offset
) {}