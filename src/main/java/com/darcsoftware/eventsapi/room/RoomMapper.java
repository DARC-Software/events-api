package com.darcsoftware.eventsapi.room;

import com.darcsoftware.eventsapi.room.dto.RoomResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoomMapper {

    @Select("""
      SELECT id, venue_id AS venueId, name, created_at AS createdAt, updated_at AS updatedAt
      FROM room WHERE id = #{id}
      """)
    Optional<RoomResponse> get(long id);

    @Select("""
      SELECT id, venue_id AS venueId, name, created_at AS createdAt, updated_at AS updatedAt
      FROM room WHERE venue_id = #{venueId}
      ORDER BY name ASC, id ASC
      """)
    List<RoomResponse> listByVenue(long venueId);

    @Insert("""
      INSERT INTO room (venue_id, name) VALUES (#{venueId}, #{name})
      """)
    int insert(@Param("venueId") long venueId, @Param("name") String name);

    @Update("UPDATE room SET name = #{name} WHERE id = #{id}")
    int rename(@Param("id") long id, @Param("name") String name);

    @Delete("DELETE FROM room WHERE id = #{id}")
    int delete(long id);
}