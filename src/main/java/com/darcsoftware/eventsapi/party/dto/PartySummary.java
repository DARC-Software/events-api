package com.darcsoftware.eventsapi.party.dto;

public record PartySummary(
        Long id,
        String displayName,
        PartyType type,
        String slug
) {}