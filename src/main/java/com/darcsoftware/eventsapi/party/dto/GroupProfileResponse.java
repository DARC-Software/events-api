package com.darcsoftware.eventsapi.party.dto;

public record GroupProfileResponse(
        Long partyId,
        String groupName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}