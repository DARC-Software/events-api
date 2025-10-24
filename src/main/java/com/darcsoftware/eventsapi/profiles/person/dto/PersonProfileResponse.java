package com.darcsoftware.eventsapi.profiles.person.dto;

public record PersonProfileResponse(
        long partyId,
        String firstName,
        String lastName,
        String stageName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}