package com.darcsoftware.eventsapi.party.dto;

public record PartyLookupItem(
        long id,
        String displayName,
        PartyType type,
        String slug
) {}