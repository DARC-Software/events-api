// party/dto/CreateGroupWithPartyRequest.java
package com.darcsoftware.eventsapi.party.dto;

/** Server will generate slug from displayName (or groupName if you prefer). */
public record CreateGroupWithPartyRequest(
        String displayName,
        String groupName,
        String bio,
        String avatarUrl,
        String instagram,
        String tiktok,
        String facebook
) {}