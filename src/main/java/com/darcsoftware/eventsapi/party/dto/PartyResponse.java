package com.darcsoftware.eventsapi.party.dto;

public record PartyResponse(
        long id,
        PartyType type,
        String displayName,
        String slug
) {}