package com.darcsoftware.eventsapi.profiles.dto;

import com.darcsoftware.eventsapi.party.dto.PartyResponse;

public record PartyWithPersonProfileResponse(
        PartyResponse party,
        PersonProfileResponse profile
) {}
