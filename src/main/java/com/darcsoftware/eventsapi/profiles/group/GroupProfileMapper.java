package com.darcsoftware.eventsapi.profiles.group;

import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileGetResponse;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileUpsertRequest;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface GroupProfileMapper {

    @Select("""
      SELECT p.id AS partyId, p.display_name AS displayName, p.slug AS slug,
             gp.group_name AS groupName, gp.bio AS bio,
             gp.avatar_url AS avatarUrl, gp.instagram AS instagram,
             gp.tiktok AS tiktok, gp.facebook AS facebook
      FROM party p
      LEFT JOIN group_profile gp ON gp.party_id = p.id
      WHERE p.id = #{partyId} AND p.type = 'GROUP'
    """)
    Optional<GroupProfileGetResponse> get(long partyId);

    @Select("""
        SELECT
          p.id           AS partyId,
          p.display_name AS displayName,
          p.slug         AS slug,
          gp.group_name  AS groupName,
          gp.bio         AS bio,
          gp.avatar_url  AS avatarUrl,
          gp.instagram   AS instagram,
          gp.tiktok      AS tiktok,
          gp.facebook    AS facebook
        FROM party p
        LEFT JOIN group_profile gp ON gp.party_id = p.id
        WHERE p.id = #{partyId}
    """)
    GroupProfileGetResponse selectGroupProfileGet(@Param("partyId") long partyId);

    @Insert("""
        INSERT INTO group_profile
          (party_id, group_name, bio, avatar_url, instagram, tiktok, facebook)
        VALUES
          (#{partyId}, #{r.groupName}, #{r.bio}, #{r.avatarUrl}, #{r.instagram}, #{r.tiktok}, #{r.facebook})
        ON CONFLICT (party_id) DO UPDATE SET
          group_name = EXCLUDED.group_name,
          bio        = EXCLUDED.bio,
          avatar_url = EXCLUDED.avatar_url,
          instagram  = EXCLUDED.instagram,
          tiktok     = EXCLUDED.tiktok,
          facebook   = EXCLUDED.facebook
    """)
    void upsertGroupProfile(@Param("partyId") long partyId,
                            @Param("r") GroupProfileUpsertRequest r);

    @Update("""
      UPDATE group_profile SET
        group_name = #{p.groupName},
        bio = #{p.bio},
        avatar_url = #{p.avatarUrl},
        instagram = #{p.instagram},
        tiktok = #{p.tiktok},
        facebook = #{p.facebook}
      WHERE party_id = #{partyId}
      """)
    int update(Long partyId, GroupProfileUpsertRequest p);

    @Delete("DELETE FROM group_profile WHERE party_id = #{partyId}")
    int delete(long partyId);
}