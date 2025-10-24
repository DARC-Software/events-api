package com.darcsoftware.eventsapi.profiles.group.dto;

public record GroupProfileGetResponse(
        long partyId,
        String displayName,
        String slug,
        String groupName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}