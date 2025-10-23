// profiles/PersonProfileService.java
package com.darcsoftware.eventsapi.profiles;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.party.dto.PartyResponse;
import com.darcsoftware.eventsapi.party.dto.PartySummary;
import com.darcsoftware.eventsapi.party.dto.PartyType;
import com.darcsoftware.eventsapi.profiles.dto.PartyWithPersonProfileResponse;
import com.darcsoftware.eventsapi.profiles.dto.PersonProfileResponse;
import com.darcsoftware.eventsapi.profiles.PersonProfileJoinRow;
import com.darcsoftware.eventsapi.profiles.PersonProfileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonProfileService {

    private final PersonProfileMapper mapper;

    public PersonProfileService(PersonProfileMapper mapper) {
        this.mapper = mapper;
    }

    public PageResponse<PartyWithPersonProfileResponse> listPeople(String q, int limit, int offset) {
        final var rows = mapper.listPeople(q, limit, offset);
        final long total = mapper.countPeople(q);

        final List<PartyWithPersonProfileResponse> items = rows.stream().map(r -> {
            final var party = new PartyResponse(
                    r.partyId(),
                    PartyType.valueOf(r.partyType()),
                    r.partyDisplayName(),
                    r.partySlug()
            );
            final var profile = new PersonProfileResponse(
                    r.partyId(),
                    r.firstName(),
                    r.lastName(),
                    r.stageName(),
                    r.bio(),
                    r.avatarUrl(),
                    r.instagram(),
                    r.tiktok(),
                    r.facebook()
            );
            return new PartyWithPersonProfileResponse(party, profile);
        }).toList();

        return new PageResponse<>(items, limit, offset, total);
    }
}