package com.darcsoftware.eventsapi.room;

import com.darcsoftware.eventsapi.room.dto.*;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Optional;

@Mapper
public interface RoomMapper {

    @Select("""
        SELECT r.id, r.venue_id AS venueId, r.name, r.created_at AS createdAt, r.updated_at AS updatedAt
        FROM room r WHERE r.id = #{id}
    """)
    Optional<RoomResponse> findById(@Param("id") Long id);

    @Select("""
    SELECT r.id, r.venue_id AS venueId, r.name, r.created_at AS createdAt, r.updated_at AS updatedAt
        FROM room r WHERE r.venue_id = #{venueId}
        ORDER BY r.name
    """)
    List<RoomResponse> listByVenue(@Param("venueId") Long venueId);

    @Select("""
        SELECT r.id, r.venue_id AS venueId, r.name, r.created_at AS createdAt, r.updated_at AS updatedAt
        FROM room r WHERE r.venue_id = #{venueId} AND r.name = #{name}
    """)
    Optional<RoomResponse> findByVenueAndName(@Param("venueId") Long venueId, @Param("name") String name);

    @Insert("""
        INSERT INTO room (venue_id, name)
        VALUES (#{venueId}, #{name})
    """)
    int insert(@Param("venueId") Long venueId, @Param("name") String name);

    @Update("""
        UPDATE room SET name = #{name} WHERE id = #{id}
    """)
    int rename(@Param("id") Long id, @Param("name") String name);

    @Delete("DELETE FROM room WHERE id = #{id}")
    int delete(@Param("id") Long id);
}