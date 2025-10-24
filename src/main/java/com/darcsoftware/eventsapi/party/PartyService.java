// party/PartyService.java
package com.darcsoftware.eventsapi.party;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.common.SlugService;
import com.darcsoftware.eventsapi.party.dto.*;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileListRow;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileResponse;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileUpsertRequest;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileListRow;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileResponse;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileUpsertRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartyService {
    private final PartyMapper party;
    private final SlugService slugService;

    public PartyService(PartyMapper party, SlugService slugService) {
        this.party = party;
        this.slugService = slugService;
    }

    // --- People list for Profiles UI ---
    public PageResponse<PersonProfileListRow> listPeople(String q, int limit, int offset) {
        return new PageResponse<>(
                party.listPeople(q, limit, offset),
                limit, offset,
                party.countPeople(q)
        );
    }

    // --- Groups list for Profiles UI ---
    public PageResponse<GroupProfileListRow> listGroups(String q, int limit, int offset) {
        return new PageResponse<>(
                party.listGroups(q, limit, offset),
                limit, offset,
                party.countGroups(q)
        );
    }

    // --- Create Person (Party + Person Profile) ---
    @Transactional
    public PartyWithPersonProfileResponse createPerson(CreatePersonWithPartyRequest req) {
        final String slug = slugService.slugOfParty(req.displayName());
        ensurePartySlugUnique(slug, null);

        party.insertParty("PERSON", req.displayName(), slug);
        long partyId = party.lastId();

        var up = new PersonProfileUpsertRequest(
                req.displayName(), req.firstName(), req.lastName(), req.stageName(), req.bio(),
                req.avatarUrl(), req.instagram(), req.tiktok(), req.facebook()
        );
        if (party.hasPersonProfile(partyId) == 0) party.insertPersonProfile(partyId, up);
        else party.updatePersonProfile(partyId, up);

        var partyResp = party.get(partyId).orElseThrow();
        var profile   = new PersonProfileResponse(partyId, req.firstName(), req.lastName(), req.stageName(), req.bio(),
                req.avatarUrl(), req.instagram(), req.tiktok(), req.facebook());

        return new PartyWithPersonProfileResponse(partyResp, profile);
    }

    // --- Create Group (Party + Group Profile) ---
    @Transactional
    public PartyWithGroupProfileResponse createGroup(CreateGroupWithPartyRequest req) {
        final String slug = slugService.slugOfParty(req.displayName());
        ensurePartySlugUnique(slug, null);

        party.insertParty("GROUP", req.displayName(), slug);
        long partyId = party.lastId();

        var up = new GroupProfileUpsertRequest(
                req.displayName(), req.groupName(), req.bio(), req.avatarUrl(), req.instagram(), req.tiktok(), req.facebook()
        );
        if (party.hasGroupProfile(partyId) == 0) party.insertGroupProfile(partyId, up);
        else party.updateGroupProfile(partyId, up);

        var partyResp = party.get(partyId).orElseThrow();
        var profile   = new GroupProfileResponse(partyId, req.groupName(), req.bio(), req.avatarUrl(),
                req.instagram(), req.tiktok(), req.facebook());

        return new PartyWithGroupProfileResponse(partyResp, profile);
    }

    // --- Group members ---
    public PageResponse<GroupMemberResponse> listMembers(long groupId, int limit, int offset) {
        return new PageResponse<>(party.listMembers(groupId, limit, offset), limit, offset, party.countMembers(groupId));
    }

    @Transactional
    public GroupMemberResponse addMember(long groupId, GroupMemberCreateRequest req) {
        party.addMember(groupId, req.memberPartyId(), req.role(), req.sortOrder());
        // return current row
        return party.listMembers(groupId, 1, 0).stream().findFirst()
                .orElse(new GroupMemberResponse(groupId, req.memberPartyId(), req.role(), req.sortOrder(), null, null));
    }

    @Transactional
    public GroupMemberResponse updateMember(long groupId, long memberPartyId, GroupMemberUpdateRequest req) {
        party.updateMember(groupId, memberPartyId, req.role(), req.sortOrder());
        return party.listMembers(groupId, 1, 0).stream().findFirst()
                .orElse(new GroupMemberResponse(groupId, memberPartyId, req.role(), req.sortOrder(), null, null));
    }

    @Transactional
    public void deleteMember(long groupId, long memberPartyId) {
        party.deleteMember(groupId, memberPartyId);
    }

    private void ensurePartySlugUnique(String slug, Long excludeId) {
        if (party.countPartySlug(slug, excludeId) > 0) {
            throw new IllegalArgumentException("Slug already exists for another party");
        }
    }
}