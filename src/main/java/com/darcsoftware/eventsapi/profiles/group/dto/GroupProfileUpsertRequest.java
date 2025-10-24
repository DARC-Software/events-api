package com.darcsoftware.eventsapi.profiles.group.dto;

/** For CRUD via /api/profiles/groups. Party is created server-side when needed. */
public record GroupProfileUpsertRequest(
        String displayName, // required on create to make the Party (GROUP)
        String groupName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}