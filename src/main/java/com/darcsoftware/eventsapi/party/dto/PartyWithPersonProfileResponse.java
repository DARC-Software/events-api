package com.darcsoftware.eventsapi.party.dto;

import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileResponse;

public record PartyWithPersonProfileResponse(
        PartyResponse party,
        PersonProfileResponse profile
) {}
