package com.darcsoftware.eventsapi.profiles;

public record PersonProfileJoinRow(
        long   partyId,
        String partyDisplayName,
        String partySlug,
        String partyType,
        String firstName,
        String lastName,
        String stageName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}