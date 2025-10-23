package com.darcsoftware.eventsapi.party.dto;

public record PartyWithGroupProfileResponse(
        PartyResponse party,
        GroupProfileResponse profile
) {}
