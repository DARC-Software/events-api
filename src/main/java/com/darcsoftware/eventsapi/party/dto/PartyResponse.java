package com.darcsoftware.eventsapi.party.dto;

public record PartyResponse(
        Long id,
        PartyType type,
        String displayName,
        String slug
) {}