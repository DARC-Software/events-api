// event/EventMapper.java
package com.darcsoftware.eventsapi.event;

import com.darcsoftware.eventsapi.event.dto.EventSummary;
import org.apache.ibatis.annotations.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface EventMapper {

    @Select("""
      SELECT e.id,
             e.title, 
             e.description, 
             e.venue_id AS venueId, 
             v.name AS venueName,
             e.room_id AS roomId, 
             r.name AS roomName,
             e.timezone, 
             e.offset_minutes AS offsetMinutes,
             e.start_time_local AS startTimeLocal, 
             e.end_time_local AS endTimeLocal,
             e.start_time_utc AS startTimeUtc, 
             e.end_time_utc AS endTimeUtc,
             e.background_url AS backgroundUrl
      FROM event e
      JOIN venue v ON v.id = e.venue_id
      LEFT JOIN room r ON r.id = e.room_id
      WHERE e.id = #{id}
      """)
    Optional<EventSummary> get(long id);

    @Insert("""
      INSERT INTO event (parent_event_id, venue_id, room_id, title, description, background_url,
                         start_time_local, end_time_local, timezone, offset_minutes,
                         start_time_utc, end_time_utc)
      VALUES (#{parentEventId}, #{venueId}, #{roomId}, #{title}, #{description}, #{backgroundUrl},
              #{startTimeLocal}, #{endTimeLocal}, #{timezone}, #{offsetMinutes},
              #{startTimeUtc}, #{endTimeUtc})
      """)
    int insert(@Param("parentEventId") Long parentEventId,
               @Param("venueId") long venueId,
               @Param("roomId") Long roomId,
               @Param("title") String title,
               @Param("description") String description,
               @Param("backgroundUrl") String backgroundUrl,
               @Param("startTimeLocal") OffsetDateTime startTimeLocal,
               @Param("endTimeLocal") OffsetDateTime endTimeLocal,
               @Param("timezone") String timezone,
               @Param("offsetMinutes") int offsetMinutes,
               @Param("startTimeUtc") OffsetDateTime startTimeUtc,
               @Param("endTimeUtc") OffsetDateTime endTimeUtc);

    @Update("""
      UPDATE event SET
        venue_id = COALESCE(#{venueId}, venue_id),
        room_id = #{roomId},
        title = COALESCE(#{title}, title),
        description = COALESCE(#{description}, description),
        background_url = COALESCE(#{backgroundUrl}, background_url),
        start_time_local = COALESCE(#{startTimeLocal}, start_time_local),
        end_time_local = COALESCE(#{endTimeLocal}, end_time_local),
        timezone = COALESCE(#{timezone}, timezone),
        offset_minutes = COALESCE(#{offsetMinutes}, offset_minutes),
        start_time_utc = COALESCE(#{startTimeUtc}, start_time_utc),
        end_time_utc = COALESCE(#{endTimeUtc}, end_time_utc)
      WHERE id = #{id}
      """)
    int update(@Param("id") long id,
               @Param("venueId") Long venueId,
               @Param("roomId") Long roomId,
               @Param("title") String title,
               @Param("description") String description,
               @Param("backgroundUrl") String backgroundUrl,
               @Param("startTimeLocal") OffsetDateTime startTimeLocal,
               @Param("endTimeLocal") OffsetDateTime endTimeLocal,
               @Param("timezone") String timezone,
               @Param("offsetMinutes") Integer offsetMinutes,
               @Param("startTimeUtc") OffsetDateTime startTimeUtc,
               @Param("endTimeUtc") OffsetDateTime endTimeUtc);

    @Delete("DELETE FROM event WHERE id = #{id}")
    int delete(long id);

    @Select("""
      SELECT e.id,
             e.title, 
             e.description, 
             e.venue_id AS venueId, 
             v.name AS venueName,
             e.room_id AS roomId, 
             r.name AS roomName,
             e.timezone, 
             e.offset_minutes AS offsetMinutes,
             e.start_time_local AS startTimeLocal, 
             e.end_time_local AS endTimeLocal,
             e.start_time_utc AS startTimeUtc, 
             e.end_time_utc AS endTimeUtc,
             e.background_url AS backgroundUrl
      FROM event e
      JOIN venue v ON v.id = e.venue_id
      LEFT JOIN room r ON r.id = e.room_id
      WHERE e.venue_id = #{venueId}
      ORDER BY e.start_time_utc DESC, e.id DESC
      LIMIT #{limit} OFFSET #{offset}
      """)
    List<EventSummary> listByVenue(@Param("venueId") long venueId, @Param("limit") int limit, @Param("offset") int offset);

    @Select("""
      SELECT COUNT(*) FROM event WHERE venue_id = #{venueId}
      """)
    long countByVenue(long venueId);

    @Select("""
      SELECT e.id,
             e.title, e.description, e.timezone, e.offset_minutes AS offsetMinutes,
             e.start_time_local AS startTimeLocal, e.end_time_local AS endTimeLocal,
             e.start_time_utc AS startTimeUtc, e.end_time_utc AS endTimeUtc,
             e.venue_id AS venueId, v.name AS venueName,
             e.room_id AS roomId, r.name AS roomName,
             e.background_url AS backgroundUrl
      FROM event e
      JOIN event_host eh ON eh.event_id = e.id
      JOIN party p ON p.id = eh.party_id
      JOIN venue v ON v.id = e.venue_id
      LEFT JOIN room r ON r.id = e.room_id
      WHERE p.id = #{hostId}
      ORDER BY e.start_time_utc DESC, e.id DESC
      LIMIT #{limit} OFFSET #{offset}
      """)
    List<EventSummary> listByHost(@Param("hostId") long hostId, @Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM event_host WHERE party_id = #{hostId}")
    long countByHost(long hostId);
}