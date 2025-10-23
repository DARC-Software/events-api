package com.darcsoftware.eventsapi.party.dto;

public record CreatePersonWithPartyRequest(
        String displayName,
        String slug,                 // optional, unique if provided
        String firstName,
        String lastName,
        String stageName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}
