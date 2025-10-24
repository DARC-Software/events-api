// profiles/dto/PersonProfileUpsertRequest.java
package com.darcsoftware.eventsapi.profiles.person.dto;

/** For CRUD via /api/profiles/persons. Party is created server-side when needed. */
public record PersonProfileUpsertRequest(
        String displayName, // required on create to make the Party (PERSON)
        String firstName,
        String lastName,
        String stageName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}