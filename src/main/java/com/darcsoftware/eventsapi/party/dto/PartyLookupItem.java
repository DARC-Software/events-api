package com.darcsoftware.eventsapi.party.dto;

public record PartyLookupItem(
        Long id,
        String displayName,
        PartyType type,
        String slug
) {}
