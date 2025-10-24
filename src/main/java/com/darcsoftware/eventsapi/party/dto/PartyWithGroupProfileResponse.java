// party/dto/PartyWithGroupProfileResponse.java
package com.darcsoftware.eventsapi.party.dto;

import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileResponse;

public record PartyWithGroupProfileResponse(
        PartyResponse party,
        GroupProfileResponse profile
) {}