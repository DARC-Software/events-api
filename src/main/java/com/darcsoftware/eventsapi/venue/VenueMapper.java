// venue/VenueMapper.java
package com.darcsoftware.eventsapi.venue;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.venue.dto.VenueResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface VenueMapper {

    // Reads
    @Select("""
      SELECT id, name, slug, address_line1 AS addressLine1, address_line2 AS addressLine2,
             city, state, zip_code AS zipCode, created_at AS createdAt, updated_at AS updatedAt
      FROM venue
      WHERE id = #{id}
      """)
    Optional<VenueResponse> get(long id);

    @Select("""
        SELECT id, name, slug,
               address_line1 AS addressLine1,
               address_line2 AS addressLine2,
               city, state, zip_code AS zipCode,
               created_at AS createdAt, updated_at AS updatedAt
        FROM venue
        ORDER BY name ASC, id ASC
        LIMIT #{limit} OFFSET #{offset}
    """)
    List<VenueResponse> list(@Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM venue")
    long countAll();

    /** Optional: expose a mapper-level wrapper so callers can just ask for a page. */
    default PageResponse<VenueResponse> listPaged(int limit, int offset) {
        List<VenueResponse> items = list(limit, offset);
        long total = countAll();
        return new PageResponse<>(items, limit, offset, total); // or `new PageResponse<>(...)` for your record
    }

    @Select("""
      SELECT id, name, slug, address_line1 AS addressLine1, address_line2 AS addressLine2,
             city, state, zip_code AS zipCode, created_at AS createdAt, updated_at AS updatedAt
      FROM venue
      WHERE slug = #{slug}
      """)
    Optional<VenueResponse> findBySlug(String slug);

    @Select("""
      SELECT COUNT(*) FROM venue WHERE slug = #{slug} AND id <> COALESCE(#{excludeId}, 0)
      """)
    long countBySlugExclude(@Param("slug") String slug, @Param("excludeId") Long excludeId);

    // Writes
    @Insert("""
      INSERT INTO venue (name, slug, address_line1, address_line2, city, state, zip_code)
      VALUES (#{name}, #{slug}, #{addressLine1}, #{addressLine2}, #{city}, #{state}, #{zipCode})
      """)
    int insert(@Param("name") String name,
               @Param("slug") String slug,
               @Param("addressLine1") String addressLine1,
               @Param("addressLine2") String addressLine2,
               @Param("city") String city,
               @Param("state") String state,
               @Param("zipCode") String zipCode);

    @Update("""
      UPDATE venue SET
        name = COALESCE(#{name}, name),
        slug = COALESCE(#{slug}, slug),
        address_line1 = COALESCE(#{addressLine1}, address_line1),
        address_line2 = COALESCE(#{addressLine2}, address_line2),
        city = COALESCE(#{city}, city),
        state = COALESCE(#{state}, state),
        zip_code = COALESCE(#{zipCode}, zip_code)
      WHERE id = #{id}
      """)
    int update(@Param("id") long id,
               @Param("name") String name,
               @Param("slug") String slug,
               @Param("addressLine1") String addressLine1,
               @Param("addressLine2") String addressLine2,
               @Param("city") String city,
               @Param("state") String state,
               @Param("zipCode") String zipCode);

    @Delete("DELETE FROM venue WHERE id = #{id}")
    int delete(long id);
}