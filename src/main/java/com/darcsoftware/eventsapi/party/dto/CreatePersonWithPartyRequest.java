// party/dto/CreatePersonWithPartyRequest.java
package com.darcsoftware.eventsapi.party.dto;

/** Server will generate slug from displayName. */
public record CreatePersonWithPartyRequest(
        String displayName,
        String firstName,
        String lastName,
        String stageName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}