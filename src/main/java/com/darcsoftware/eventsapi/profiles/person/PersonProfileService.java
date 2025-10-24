// profiles/PersonProfileCrudService.java
package com.darcsoftware.eventsapi.profiles.person;

import com.darcsoftware.eventsapi.common.error.NotFoundException;
import com.darcsoftware.eventsapi.party.PartyMapper;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileGetResponse;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileUpsertRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonProfileService {
    private final PersonProfileMapper mapper;
    private final PartyMapper partyMapper;
    private final NotFoundException notFound = new NotFoundException("Party not found");

    public PersonProfileService(PersonProfileMapper mapper, PartyMapper partyMapper) {
        this.mapper = mapper;
        this.partyMapper = partyMapper;
    }

    @Transactional
    public PersonProfileGetResponse upsertAndGet(long partyId, PersonProfileUpsertRequest req) {
        if (!partyMapper.existsById(partyId)) {
            throw new NotFoundException("Party not found");
        }
        if (req.displayName() != null && !req.displayName().isBlank()) {
            partyMapper.updateDisplayName(partyId, req.displayName());
        }
        mapper.upsertPersonProfile(partyId, req);
        var out = mapper.selectPersonProfileGet(partyId);
        if (out == null) throw new NotFoundException("Profile not found after upsert");
        return out;
    }

    @Transactional(readOnly = true)
    public PersonProfileGetResponse get(long partyId) {
        var out = mapper.selectPersonProfileGet(partyId);
        if (out == null) throw new NotFoundException("Profile not found");
        return out;
    }

    @Transactional
    public void delete(long partyId) {
        mapper.delete(partyId);
    }
}