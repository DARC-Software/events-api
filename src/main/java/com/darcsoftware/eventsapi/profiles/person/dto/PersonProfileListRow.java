package com.darcsoftware.eventsapi.profiles.person.dto;

public record PersonProfileListRow(
        long partyId,
        String displayName,
        String slug,
        String firstName,
        String lastName,
        String stageName,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}