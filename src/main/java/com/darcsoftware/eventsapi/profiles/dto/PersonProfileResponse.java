package com.darcsoftware.eventsapi.profiles.dto;

public record PersonProfileResponse(
        Long partyId,
        String firstName,
        String lastName,
        String stageName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}