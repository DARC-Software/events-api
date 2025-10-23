package com.darcsoftware.eventsapi.party;

import com.darcsoftware.eventsapi.party.dto.*;
import com.darcsoftware.eventsapi.profiles.dto.PartyWithPersonProfileResponse;
import com.darcsoftware.eventsapi.profiles.dto.PersonProfileResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartyService {

    private final PartyMapper partyMapper;

    public PartyService(PartyMapper partyMapper) {
        this.partyMapper = partyMapper;
    }

    // ===== existing create person/group methods & list(...) stay as we wrote before =====

    @Transactional
    public PartyWithPersonProfileResponse createPersonWithParty(CreatePersonWithPartyRequest req) {
        if (req.slug() != null && !req.slug().isBlank() && partyMapper.countBySlug(req.slug()) > 0) {
            throw new IllegalArgumentException("Slug already exists: " + req.slug());
        }
        partyMapper._insertPartyRaw(PartyType.PERSON, req.displayName(), emptyToNull(req.slug()));
        Long partyId = partyMapper.lastInsertId();

        partyMapper.insertPersonProfile(
                partyId, nullIfBlank(req.firstName()), nullIfBlank(req.lastName()), nullIfBlank(req.stageName()),
                nullIfBlank(req.bio()), nullIfBlank(req.avatarUrl()), nullIfBlank(req.instagram()),
                nullIfBlank(req.tiktok()), nullIfBlank(req.facebook())
        );

        var party = new PartyResponse(partyId, PartyType.PERSON, req.displayName(), emptyToNull(req.slug()));
        var profile = new PersonProfileResponse(partyId,
                nullIfBlank(req.firstName()), nullIfBlank(req.lastName()), nullIfBlank(req.stageName()),
                nullIfBlank(req.bio()), nullIfBlank(req.avatarUrl()), nullIfBlank(req.instagram()),
                nullIfBlank(req.tiktok()), nullIfBlank(req.facebook()));
        return new PartyWithPersonProfileResponse(party, profile);
    }

    @Transactional
    public PartyWithGroupProfileResponse createGroupWithParty(CreateGroupWithPartyRequest req) {
        if (req.slug() != null && !req.slug().isBlank() && partyMapper.countBySlug(req.slug()) > 0) {
            throw new IllegalArgumentException("Slug already exists: " + req.slug());
        }
        partyMapper._insertPartyRaw(PartyType.GROUP, req.displayName(), emptyToNull(req.slug()));
        Long partyId = partyMapper.lastInsertId();

        partyMapper.insertGroupProfile(
                partyId, req.groupName(), nullIfBlank(req.bio()), nullIfBlank(req.avatarUrl()),
                nullIfBlank(req.instagram()), nullIfBlank(req.tiktok()), nullIfBlank(req.facebook())
        );

        var party = new PartyResponse(partyId, PartyType.GROUP, req.displayName(), emptyToNull(req.slug()));
        var profile = new GroupProfileResponse(partyId, req.groupName(), nullIfBlank(req.bio()),
                nullIfBlank(req.avatarUrl()), nullIfBlank(req.instagram()), nullIfBlank(req.tiktok()),
                nullIfBlank(req.facebook()));
        return new PartyWithGroupProfileResponse(party, profile);
    }

    public PartyListResponse list(PartyListQuery q) {
        int limit = q.page() != null && q.page().limit() != null ? q.page().limit() : 50;
        int offset = q.page() != null && q.page().offset() != null ? q.page().offset() : 0;
        var items = partyMapper.listParties(q.type(), q.q(), limit, offset);
        var total = partyMapper.countParties(q.type(), q.q());
        return new PartyListResponse(items, limit, offset, total);
    }

    // ===== NEW: group members =====
    @Transactional
    public GroupMemberResponse addMember(Long groupId, GroupMemberCreateRequest req) {
        ensurePartyType(groupId, PartyType.GROUP, "groupId");
        ensurePartyType(req.memberPartyId(), PartyType.PERSON, "memberPartyId");

        if (partyMapper.countGroupMember(groupId, req.memberPartyId()) > 0) {
            throw new IllegalArgumentException("Member already in group.");
        }

        partyMapper.insertGroupMember(groupId, req.memberPartyId(), nullIfBlank(req.role()), req.sortOrder());
        // fetch denormalized bits
        var member = partyMapper.findPartyById(req.memberPartyId());
        return new GroupMemberResponse(groupId, req.memberPartyId(),
                nullIfBlank(req.role()), req.sortOrder() == null ? 0 : req.sortOrder(),
                member != null ? member.displayName() : null,
                member != null ? member.slug() : null);
    }

    @Transactional
    public GroupMemberResponse updateMember(Long groupId, Long memberPartyId, GroupMemberUpdateRequest req) {
        ensurePartyType(groupId, PartyType.GROUP, "groupId");
        ensurePartyType(memberPartyId, PartyType.PERSON, "memberPartyId");

        partyMapper.updateGroupMember(groupId, memberPartyId, nullIfBlank(req.role()), req.sortOrder());

        var member = partyMapper.findPartyById(memberPartyId);
        // We don’t re-read role/sort from DB here; if you prefer, SELECT back the row.
        return new GroupMemberResponse(groupId, memberPartyId,
                nullIfBlank(req.role()), req.sortOrder(),
                member != null ? member.displayName() : null,
                member != null ? member.slug() : null);
    }

    @Transactional
    public void removeMember(Long groupId, Long memberPartyId) {
        ensurePartyType(groupId, PartyType.GROUP, "groupId");
        ensurePartyType(memberPartyId, PartyType.PERSON, "memberPartyId");
        partyMapper.deleteGroupMember(groupId, memberPartyId);
    }

    public GroupMemberListResponse listMembers(Long groupId, Integer limit, Integer offset) {
        ensurePartyType(groupId, PartyType.GROUP, "groupId");
        int l = limit == null ? 50 : limit;
        int o = offset == null ? 0 : offset;
        var items = partyMapper.listGroupMembers(groupId, l, o);
        var total = partyMapper.countGroupMembers(groupId);
        return new GroupMemberListResponse(items, l, o, total);
    }

    // ===== helpers =====
    private void ensurePartyType(Long partyId, PartyType expected, String paramName) {
        var type = partyMapper.findPartyType(partyId);
        if (type == null) throw new IllegalArgumentException("Unknown partyId for " + paramName + ": " + partyId);
        if (type != expected) {
            throw new IllegalArgumentException("partyId " + partyId + " must be type " + expected + " for " + paramName);
        }
    }

    private static String emptyToNull(String s) { return (s == null || s.isBlank()) ? null : s; }
    private static String nullIfBlank(String s) { return (s == null || s.isBlank()) ? null : s; }
}