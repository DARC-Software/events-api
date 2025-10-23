package com.darcsoftware.eventsapi.party.dto;

public record CreateGroupWithPartyRequest(
        String displayName,
        String slug,                 // optional, unique if provided
        String groupName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}
