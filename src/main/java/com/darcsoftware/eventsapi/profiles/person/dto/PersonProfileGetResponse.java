package com.darcsoftware.eventsapi.profiles.person.dto;

public record PersonProfileGetResponse(
        long partyId,
        String displayName,
        String slug,
        String firstName,
        String lastName,
        String stageName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}