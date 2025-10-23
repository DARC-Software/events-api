package com.darcsoftware.eventsapi.venue;

import com.darcsoftware.eventsapi.venue.dto.*;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Optional;

@Mapper
public interface VenueMapper {

    // Reads
    @Select("""
        SELECT id, name, slug, address_line1 AS addressLine1, address_line2 AS addressLine2,
               city, state, zip_code AS zipCode, created_at AS createdAt, updated_at AS updatedAt
        FROM venue WHERE id = #{id}
    """)
    Optional<VenueResponse> findById(@Param("id") Long id);

    @Select("""
        SELECT id, name, slug, address_line1 AS addressLine1, address_line2 AS addressLine2,
               city, state, zip_code AS zipCode, created_at AS createdAt, updated_at AS updatedAt
        FROM venue WHERE slug = #{slug}
    """)
    Optional<VenueResponse> findBySlug(@Param("slug") String slug);

    @Select("""
        SELECT id, name, slug, address_line1 AS addressLine1, address_line2 AS addressLine2,
               city, state, zip_code AS zipCode, created_at AS createdAt, updated_at AS updatedAt
        FROM venue
        ORDER BY created_at DESC
        LIMIT #{limit} OFFSET #{offset}
    """)
    List<VenueResponse> list(@Param("limit") int limit, @Param("offset") int offset);

    // Writes
    @Insert("""
        INSERT INTO venue (name, slug, address_line1, address_line2, city, state, zip_code)
        VALUES (#{v.name}, #{v.slug}, #{v.addressLine1}, #{v.addressLine2}, #{v.city}, #{v.state}, #{v.zipCode})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "v.id")
    int insert(@Param("v") VenueCreateRequest v);

    @Update("""
        UPDATE venue
        SET name = #{req.name},
            slug = #{req.slug},
            address_line1 = #{req.addressLine1},
            address_line2 = #{req.addressLine2},
            city = #{req.city},
            state = #{req.state},
            zip_code = #{req.zipCode}
        WHERE id = #{id}
    """)
    int update(@Param("id") Long id, @Param("req") VenueUpdateRequest req);

    @Delete("DELETE FROM venue WHERE id = #{id}")
    int delete(@Param("id") Long id);
}