// profiles/GroupProfileCrudService.java
package com.darcsoftware.eventsapi.profiles.group;

import com.darcsoftware.eventsapi.common.error.NotFoundException;
import com.darcsoftware.eventsapi.party.PartyMapper;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileGetResponse;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileUpsertRequest;
import com.darcsoftware.eventsapi.profiles.group.GroupProfileMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupProfileService {
    private final GroupProfileMapper mapper;
    private final PartyMapper partyMapper;
    private final NotFoundException notFound = new NotFoundException("Party not found");

    public GroupProfileService(GroupProfileMapper mapper, PartyMapper partyMapper) {
        this.mapper = mapper;
        this.partyMapper = partyMapper;
    }

    @Transactional
    public GroupProfileGetResponse upsertAndGet(long partyId, GroupProfileUpsertRequest req) {
        if (!partyMapper.existsById(partyId)) {
            throw new NotFoundException("Party not found");
        }
        if (req.displayName() != null && !req.displayName().isBlank()) {
            partyMapper.updateDisplayName(partyId, req.displayName());
        }
        mapper.upsertGroupProfile(partyId, req);
        var out = mapper.selectGroupProfileGet(partyId);
        if (out == null) throw new NotFoundException("Profile not found after upsert");
        return out;
    }

    @Transactional(readOnly = true)
    public GroupProfileGetResponse get(long partyId) {
        var out = mapper.selectGroupProfileGet(partyId);
        if (out == null) throw new NotFoundException("Profile not found");
        return out;
    }

    @Transactional
    public void delete(long partyId) { mapper.delete(partyId); }
}