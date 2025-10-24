// party/PartyMapper.java
package com.darcsoftware.eventsapi.party;

import com.darcsoftware.eventsapi.party.dto.*;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileListRow;
import com.darcsoftware.eventsapi.profiles.group.dto.GroupProfileUpsertRequest;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileListRow;
import com.darcsoftware.eventsapi.profiles.person.dto.PersonProfileUpsertRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PartyMapper {

    // --- Party base ---
    @Insert("""
      INSERT INTO party (type, display_name, slug)
      VALUES (#{type}, #{displayName}, #{slug})
      """)
    int insertParty(@Param("type") String type,
                    @Param("displayName") String displayName,
                    @Param("slug") String slug);

    @Select("SELECT LAST_INSERT_ID()")
    long lastId();

    @Select("""
      SELECT id, type, display_name AS displayName, slug
      FROM party
      WHERE id = #{id}
      """)
    Optional<PartyResponse> get(long id);

    @Select("""
      SELECT id, type, display_name AS displayName, slug
      FROM party
      WHERE slug = #{slug}
      """)
    Optional<PartyResponse> findBySlug(String slug);

    @Select("""
      SELECT COUNT(*) FROM party WHERE slug = #{slug} AND id <> COALESCE(#{excludeId}, 0)
      """)
    long countPartySlug(@Param("slug") String slug, @Param("excludeId") Long excludeId);

    @Select("""
      <script>
      SELECT id, display_name AS displayName, type, slug
      FROM party
      <where>
        <if test="type != null"> type = #{type} </if>
        <if test="q != null and q != ''">
          AND (LOWER(display_name) LIKE CONCAT('%', LOWER(#{q}), '%')
               OR LOWER(slug) LIKE CONCAT('%', LOWER(#{q}), '%'))
        </if>
      </where>
      ORDER BY display_name ASC, id ASC
      LIMIT #{limit} OFFSET #{offset}
      </script>
      """)
    List<PartyLookupItem> list(@Param("type") String type,
                               @Param("q") String q,
                               @Param("limit") int limit,
                               @Param("offset") int offset);

    @Select("""
      <script>
      SELECT COUNT(*) FROM party
      <where>
        <if test="type != null"> type = #{type} </if>
        <if test="q != null and q != ''">
          AND (LOWER(display_name) LIKE CONCAT('%', LOWER(#{q}), '%')
               OR LOWER(slug) LIKE CONCAT('%', LOWER(#{q}), '%'))
        </if>
      </where>
      </script>
      """)
    long count(@Param("type") String type, @Param("q") String q);

    // --- Person profile ---
    @Insert("""
      INSERT INTO person_profile (party_id, first_name, last_name, stage_name, bio, avatar_url, instagram, tiktok, facebook)
      VALUES (#{partyId}, #{p.firstName}, #{p.lastName}, #{p.stageName}, #{p.bio}, #{p.avatarUrl}, #{p.instagram}, #{p.tiktok}, #{p.facebook})
      """)
    int insertPersonProfile(Long partyId, PersonProfileUpsertRequest p);

    @Update("""
      UPDATE person_profile SET
        first_name = #{firstName},
        last_name = #{lastName},
        stage_name = #{stageName},
        bio = #{bio},
        avatar_url = #{avatarUrl},
        instagram = #{instagram},
        tiktok = #{tiktok},
        facebook = #{facebook}
      WHERE party_id = #{partyId}
      """)
    int updatePersonProfile(Long partyId, PersonProfileUpsertRequest p);

    @Select("SELECT COUNT(*) FROM person_profile WHERE party_id = #{partyId}")
    long hasPersonProfile(long partyId);

    // --- Group profile ---
    @Insert("""
      INSERT INTO group_profile (party_id, group_name, bio, avatar_url, instagram, tiktok, facebook)
      VALUES (#{partyId}, #{p.groupName}, #{p.bio}, #{p.avatarUrl}, #{p.instagram}, #{p.tiktok}, #{p.facebook})
      """)
    int insertGroupProfile(Long partyId, GroupProfileUpsertRequest p);

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
    int updateGroupProfile(Long partyId, GroupProfileUpsertRequest p);

    @Select("SELECT COUNT(*) FROM group_profile WHERE party_id = #{partyId}")
    long hasGroupProfile(long partyId);

    // --- Group members ---
    @Insert("""
      INSERT INTO group_member (group_id, member_id, role, sort_order)
      VALUES (#{groupId}, #{memberPartyId}, #{role}, COALESCE(#{sortOrder},0))
      """)
    int addMember(@Param("groupId") long groupId,
                  @Param("memberPartyId") long memberPartyId,
                  @Param("role") String role,
                  @Param("sortOrder") Integer sortOrder);

    @Update("""
      UPDATE group_member SET role = #{role}, sort_order = COALESCE(#{sortOrder}, sort_order)
      WHERE group_id = #{groupId} AND member_id = #{memberPartyId}
      """)
    int updateMember(@Param("groupId") long groupId,
                     @Param("memberPartyId") long memberPartyId,
                     @Param("role") String role,
                     @Param("sortOrder") Integer sortOrder);

    @Delete("DELETE FROM group_member WHERE group_id = #{groupId} AND member_id = #{memberPartyId}")
    int deleteMember(@Param("groupId") long groupId, @Param("memberPartyId") long memberPartyId);

    @Select("""
      SELECT gm.group_id AS groupId,
             gm.member_id AS memberPartyId,
             gm.role,
             gm.sort_order AS sortOrder,
             p.display_name AS memberDisplayName,
             p.slug AS memberSlug
      FROM group_member gm
      JOIN party p ON p.id = gm.member_id
      WHERE gm.group_id = #{groupId}
      ORDER BY gm.sort_order ASC, p.display_name ASC
      LIMIT #{limit} OFFSET #{offset}
      """)
    List<GroupMemberResponse> listMembers(@Param("groupId") long groupId,
                                          @Param("limit") int limit,
                                          @Param("offset") int offset);

    @Select("""
      SELECT COUNT(*) FROM group_member WHERE group_id = #{groupId}
      """)
    long countMembers(long groupId);


    // --- Listing joins for profiles (read side for CRUD pages) ---

    @Select("""
      SELECT p.id AS partyId, p.display_name AS displayName, p.slug AS slug,
             pp.first_name AS firstName, pp.last_name AS lastName,
             pp.stage_name AS stageName, pp.bio AS bio,
             pp.avatar_url AS avatarUrl, pp.instagram AS instagram,
             pp.tiktok AS tiktok, pp.facebook AS facebook
      FROM party p
      LEFT JOIN person_profile pp ON pp.party_id = p.id
      WHERE p.type = 'PERSON'
        AND (#{q} IS NULL OR #{q} = '' OR
             LOWER(CONCAT_WS(' ', p.display_name, p.slug, pp.first_name, pp.last_name, pp.stage_name, pp.instagram, pp.tiktok, pp.facebook))
             LIKE CONCAT('%', LOWER(#{q}), '%'))
      ORDER BY p.display_name ASC, p.id ASC
      LIMIT #{limit} OFFSET #{offset}
      """)
    List<PersonProfileListRow> listPeople(@Param("q") String q, @Param("limit") int limit, @Param("offset") int offset);

    @Select("""
      SELECT COUNT(*) FROM party p
      LEFT JOIN person_profile pp ON pp.party_id = p.id
      WHERE p.type = 'PERSON'
        AND (#{q} IS NULL OR #{q} = '' OR
             LOWER(CONCAT_WS(' ', p.display_name, p.slug, pp.first_name, pp.last_name, pp.stage_name, pp.instagram, pp.tiktok, pp.facebook))
             LIKE CONCAT('%', LOWER(#{q}), '%'))
      """)
    long countPeople(@Param("q") String q);

    @Select("""
      SELECT p.id AS partyId, p.display_name AS displayName, p.slug AS slug,
             gp.group_name AS groupName, gp.bio AS bio,
             gp.avatar_url AS avatarUrl, gp.instagram AS instagram,
             gp.tiktok AS tiktok, gp.facebook AS facebook
      FROM party p
      LEFT JOIN group_profile gp ON gp.party_id = p.id
      WHERE p.type = 'GROUP'
        AND (#{q} IS NULL OR #{q} = '' OR
             LOWER(CONCAT_WS(' ', p.display_name, p.slug, gp.group_name, gp.instagram, gp.tiktok, gp.facebook))
             LIKE CONCAT('%', LOWER(#{q}), '%'))
      ORDER BY p.display_name ASC, p.id ASC
      LIMIT #{limit} OFFSET #{offset}
      """)
    List<GroupProfileListRow> listGroups(@Param("q") String q, @Param("limit") int limit, @Param("offset") int offset);

    @Select("""
      SELECT COUNT(*) FROM party p
      LEFT JOIN group_profile gp ON gp.party_id = p.id
      WHERE p.type = 'GROUP'
        AND (#{q} IS NULL OR #{q} = '' OR
             LOWER(CONCAT_WS(' ', p.display_name, p.slug, gp.group_name, gp.instagram, gp.tiktok, gp.facebook))
             LIKE CONCAT('%', LOWER(#{q}), '%'))
      """)
    long countGroups(@Param("q") String q);

    @Select("SELECT EXISTS (SELECT 1 FROM party WHERE id = #{partyId})")
    boolean existsById(@Param("partyId") long partyId);

    @Update("""
    UPDATE party
       SET display_name = #{displayName},
           updated_at   = NOW()
     WHERE id = #{partyId}
  """)
    int updateDisplayName(@Param("partyId") long partyId,
                          @Param("displayName") String displayName);
}