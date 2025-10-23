package com.darcsoftware.eventsapi.profiles;

import org.apache.ibatis.jdbc.SQL;

public class PersonProfileSql {

    public String listPeopleSql(final String q, final int limit, final int offset) {
        // Build base SELECT with exact aliases used by @ConstructorArgs
        String base = new SQL(){{
            SELECT("""
        p.id           AS party_id,
        p.display_name AS party_display_name,
        p.slug         AS party_slug,
        p.type         AS party_type,
        pp.first_name  AS first_name,
        pp.last_name   AS last_name,
        pp.stage_name  AS stage_name,
        pp.bio         AS bio,
        pp.avatar_url  AS avatar_url,
        pp.instagram   AS instagram,
        pp.tiktok      AS tiktok,
        pp.facebook    AS facebook
      """);
            FROM("party p");
            LEFT_OUTER_JOIN("person_profile pp ON pp.party_id = p.id");
            WHERE("p.type = 'PERSON'");
            // optional search (case-insensitive LIKE on multiple fields)
            if (q != null && !q.isBlank()) {
                String like = "'%' || #{q} || '%'";
                // MySQL uses CONCAT, so construct safely without vendor functions in SQL builder:
                // We'll just append raw WHERE with CONCAT since MyBatis JDBC SQL builder is limited
            }
            ORDER_BY("p.display_name ASC, p.id ASC");
        }}.toString();

        // add optional search (MySQL CONCAT) & pagination
        StringBuilder sql = new StringBuilder(base);
        if (q != null && !q.isBlank()) {
            sql.append("""
        AND (
          LOWER(p.display_name) LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(p.slug)         LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.first_name)  LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.last_name)   LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.stage_name)  LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.instagram)   LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.tiktok)      LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.facebook)    LIKE LOWER(CONCAT('%', #{q}, '%'))
        )
      """);
        }
        sql.append(" LIMIT #{limit} OFFSET #{offset}");
        return sql.toString();
    }

    public String countPeopleSql(final String q) {
        StringBuilder sql = new StringBuilder("""
      SELECT COUNT(*)
      FROM party p
      LEFT JOIN person_profile pp ON pp.party_id = p.id
      WHERE p.type = 'PERSON'
    """);
        if (q != null && !q.isBlank()) {
            sql.append("""
        AND (
          LOWER(p.display_name) LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(p.slug)         LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.first_name)  LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.last_name)   LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.stage_name)  LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.instagram)   LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.tiktok)      LIKE LOWER(CONCAT('%', #{q}, '%')) OR
          LOWER(pp.facebook)    LIKE LOWER(CONCAT('%', #{q}, '%'))
        )
      """);
        }
        return sql.toString();
    }
}