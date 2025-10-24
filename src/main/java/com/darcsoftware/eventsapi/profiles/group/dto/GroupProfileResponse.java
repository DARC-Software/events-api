package com.darcsoftware.eventsapi.profiles.group.dto;

public record GroupProfileResponse(
        long partyId,
        String groupName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}