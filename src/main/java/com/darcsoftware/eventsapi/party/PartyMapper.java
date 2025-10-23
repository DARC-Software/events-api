package com.darcsoftware.eventsapi.party;

import com.darcsoftware.eventsapi.party.dto.PartyType;
import com.darcsoftware.eventsapi.party.dto.PartyLookupItem;
import com.darcsoftware.eventsapi.party.dto.GroupMemberResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PartyMapper {

    // ===== party =====
    @Insert("""
    INSERT INTO party (type, display_name, slug)
    VALUES (#{type}, #{displayName}, #{slug})
  """)
    int _insertPartyRaw(@Param("type") PartyType type,
                        @Param("displayName") String displayName,
                        @Param("slug") String slug);

    @Select("SELECT LAST_INSERT_ID()")
    Long lastInsertId();

    @Select("SELECT COUNT(*) FROM party WHERE slug = #{slug}")
    long countBySlug(String slug);

    @Select("""
    SELECT id, display_name, type, slug
    FROM party
    WHERE id = #{partyId}
  """)
    @Results(id="partyLookupMap", value={
            @Result(column="id", property="id"),
            @Result(column="display_name", property="displayName"),
            @Result(column="type", property="type"),
            @Result(column="slug", property="slug")
    })
    PartyLookupItem findPartyById(Long partyId);

    @Select("""
    SELECT type FROM party WHERE id = #{partyId}
  """)
    PartyType findPartyType(Long partyId);

    @Select("""
    SELECT id, display_name, type, slug
    FROM party
    WHERE slug = #{slug}
    LIMIT 1
  """)
    @ResultMap("partyLookupMap")
    PartyLookupItem findPartyBySlug(String slug);

    // ===== person_profile =====
    @Insert("""
    INSERT INTO person_profile
      (party_id, first_name, last_name, stage_name, bio, avatar_url, instagram, tiktok, facebook)
    VALUES
      (#{partyId}, #{firstName}, #{lastName}, #{stageName}, #{bio}, #{avatarUrl}, #{instagram}, #{tiktok}, #{facebook})
  """)
    int insertPersonProfile(@Param("partyId") Long partyId,
                            @Param("firstName") String firstName,
                            @Param("lastName") String lastName,
                            @Param("stageName") String stageName,
                            @Param("bio") String bio,
                            @Param("avatarUrl") String avatarUrl,
                            @Param("instagram") String instagram,
                            @Param("tiktok") String tiktok,
                            @Param("facebook") String facebook);

    // ===== group_profile =====
    @Insert("""
    INSERT INTO group_profile
      (party_id, group_name, bio, avatar_url, instagram, tiktok, facebook)
    VALUES
      (#{partyId}, #{groupName}, #{bio}, #{avatarUrl}, #{instagram}, #{tiktok}, #{facebook})
  """)
    int insertGroupProfile(@Param("partyId") Long partyId,
                           @Param("groupName") String groupName,
                           @Param("bio") String bio,
                           @Param("avatarUrl") String avatarUrl,
                           @Param("instagram") String instagram,
                           @Param("tiktok") String tiktok,
                           @Param("facebook") String facebook);

    // ===== listing parties for admin lookups =====
    @Select("""
    <script>
      SELECT p.id, p.display_name, p.type, p.slug
      FROM party p
      <where>
        <if test="type != null">
          AND p.type = #{type}
        </if>
        <if test="q != null and q != ''">
          AND (p.display_name LIKE CONCAT('%', #{q}, '%')
               OR p.slug LIKE CONCAT('%', #{q}, '%'))
        </if>
      </where>
      ORDER BY p.display_name ASC
      LIMIT #{limit} OFFSET #{offset}
    </script>
  """)
    List<PartyLookupItem> listParties(@Param("type") PartyType type,
                                      @Param("q") String q,
                                      @Param("limit") int limit,
                                      @Param("offset") int offset);

    @Select("""
    <script>
      SELECT COUNT(*)
      FROM party p
      <where>
        <if test="type != null">
          AND p.type = #{type}
        </if>
        <if test="q != null and q != ''">
          AND (p.display_name LIKE CONCAT('%', #{q}, '%')
               OR p.slug LIKE CONCAT('%', #{q}, '%'))
        </if>
      </where>
    </script>
  """)
    long countParties(@Param("type") PartyType type, @Param("q") String q);

    // ===== group_member =====
    @Insert("""
    INSERT INTO group_member (group_id, member_id, role, sort_order)
    VALUES (#{groupId}, #{memberId}, #{role}, COALESCE(#{sortOrder}, 0))
  """)
    int insertGroupMember(@Param("groupId") Long groupId,
                          @Param("memberId") Long memberId,
                          @Param("role") String role,
                          @Param("sortOrder") Integer sortOrder);

    @Select("""
    SELECT COUNT(*) FROM group_member
    WHERE group_id = #{groupId} AND member_id = #{memberId}
  """)
    long countGroupMember(@Param("groupId") Long groupId,
                          @Param("memberId") Long memberId);

    @Delete("""
    DELETE FROM group_member
    WHERE group_id = #{groupId} AND member_id = #{memberId}
  """)
    int deleteGroupMember(@Param("groupId") Long groupId,
                          @Param("memberId") Long memberId);

    @Update("""
    UPDATE group_member
    SET role = COALESCE(#{role}, role),
        sort_order = COALESCE(#{sortOrder}, sort_order)
    WHERE group_id = #{groupId} AND member_id = #{memberId}
  """)
    int updateGroupMember(@Param("groupId") Long groupId,
                          @Param("memberId") Long memberId,
                          @Param("role") String role,
                          @Param("sortOrder") Integer sortOrder);

    @Select("""
    <script>
      SELECT
        gm.group_id      AS groupId,
        gm.member_id     AS memberPartyId,
        gm.role          AS role,
        gm.sort_order    AS sortOrder,
        p.display_name   AS memberDisplayName,
        p.slug           AS memberSlug
      FROM group_member gm
      JOIN party p ON p.id = gm.member_id
      WHERE gm.group_id = #{groupId}
      ORDER BY gm.sort_order ASC, p.display_name ASC
      LIMIT #{limit} OFFSET #{offset}
    </script>
  """)
    @Results(id="groupMemberResult", value = {
            @Result(column="groupId", property="groupId"),
            @Result(column="memberPartyId", property="memberPartyId"),
            @Result(column="role", property="role"),
            @Result(column="sortOrder", property="sortOrder"),
            @Result(column="memberDisplayName", property="memberDisplayName"),
            @Result(column="memberSlug", property="memberSlug")
    })
    List<GroupMemberResponse> listGroupMembers(@Param("groupId") Long groupId,
                                               @Param("limit") int limit,
                                               @Param("offset") int offset);

    @Select("""
    SELECT COUNT(*)
    FROM group_member gm
    WHERE gm.group_id = #{groupId}
  """)
    long countGroupMembers(Long groupId);
}