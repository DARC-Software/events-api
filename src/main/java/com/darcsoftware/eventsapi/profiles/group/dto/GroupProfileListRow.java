package com.darcsoftware.eventsapi.profiles.group.dto;

public record GroupProfileListRow(
        long partyId,
        String displayName,
        String slug,
        String groupName,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}