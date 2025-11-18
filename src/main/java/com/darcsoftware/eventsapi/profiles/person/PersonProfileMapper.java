// profiles/PersonProfileCrudMapper.java
package com.darcsoftware.eventsapi.profiles.person;

import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileGetResponse;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileUpsertRequest;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface PersonProfileMapper {

    @Select("""
        SELECT p.id AS partyId, p.display_name AS displayName, p.slug AS slug,
           pp.first_name AS firstName, pp.last_name AS lastName, pp.stage_name AS stageName,
           pp.bio AS bio, pp.avatar_url AS avatarUrl, pp.instagram AS instagram,
           pp.tiktok AS tiktok, pp.facebook AS facebook
        FROM party p
        LEFT JOIN person_profile pp ON pp.party_id = p.id
        WHERE p.id = #{partyId} AND p.type = 'PERSON'
    """)
    Optional<PersonProfileGetResponse> get(long partyId);

    @Select("""
        SELECT
          p.id           AS partyId,
          p.display_name AS displayName,
          p.slug         AS slug,
          pp.first_name  AS firstName,
          pp.last_name   AS lastName,
          pp.stage_name  AS stageName,
          pp.bio         AS bio,
          pp.avatar_url  AS avatarUrl,
          pp.instagram   AS instagram,
          pp.tiktok      AS tiktok,
          pp.facebook    AS facebook
        FROM party p
        LEFT JOIN person_profile pp ON pp.party_id = p.id
        WHERE p.id = #{partyId}
    """)
    PersonProfileGetResponse selectPersonProfileGet(@Param("partyId") long partyId);

    @Insert("""
      INSERT INTO person_profile
        (party_id, first_name, last_name, stage_name, bio, avatar_url, instagram, tiktok, facebook)
      VALUES
        (#{partyId}, #{r.firstName}, #{r.lastName}, #{r.stageName}, #{r.bio},
         #{r.avatarUrl}, #{r.instagram}, #{r.tiktok}, #{r.facebook})
      AS new
      ON DUPLICATE KEY UPDATE
        first_name = new.first_name,
        last_name  = new.last_name,
        stage_name = new.stage_name,
        bio        = new.bio,
        avatar_url = new.avatar_url,
        instagram  = new.instagram,
        tiktok     = new.tiktok,
        facebook   = new.facebook
    """)
    void upsertPersonProfile(@Param("partyId") long partyId,
                             @Param("r") PersonProfileUpsertRequest r);

    @Update("""
      UPDATE person_profile SET
        first_name = #{p.firstName},
        last_name = #{p.lastName},
        stage_name = #{p.stageName},
        bio = #{p.bio},
        avatar_url = #{p.avatarUrl},
        instagram = #{p.instagram},
        tiktok = #{p.tiktok},
        facebook = #{p.facebook}
      WHERE party_id = #{partyId}
      """)
    int update(Long partyId, PersonProfileUpsertRequest p);

    @Delete("DELETE FROM person_profile WHERE party_id = #{partyId}")
    int delete(long partyId);
}