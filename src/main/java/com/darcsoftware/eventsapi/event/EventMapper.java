package com.darcsoftware.eventsapi.event;

import com.darcsoftware.eventsapi.event.dto.*;
import org.apache.ibatis.annotations.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface EventMapper {

    // Reads (summary shape you defined earlier)
    @Select("""
    SELECT
      e.id,
      e.title,
      e.description,
      e.venue_id       AS venueId,
      v.name           AS venueName,
      e.room_id        AS roomId,
      r.name           AS roomName,
      e.timezone,
      e.offset_minutes AS offsetMinutes,
      e.start_time_local AS startTimeLocal,
      e.end_time_local   AS endTimeLocal,
      e.start_time_utc    AS startTimeUtc,
      e.end_time_utc      AS endTimeUtc,
      e.background_url   AS backgroundUrl
    FROM event e
    JOIN venue v ON v.id = e.venue_id
    LEFT JOIN room r ON r.id = e.room_id
    WHERE e.id = #{id}
  """)
    Optional<EventSummary> findSummaryById(@Param("id") Long id);

    @Select("""
    SELECT
      e.id, e.title, e.description,
      e.venue_id AS venueId, v.name AS venueName,
      e.room_id AS roomId, r.name AS roomName,
      e.timezone, e.offset_minutes AS offsetMinutes,
      e.start_time_local AS startTimeLocal, e.end_time_local AS endTimeLocal,
      e.start_time_utc AS startTimeUtc,   e.end_time_utc AS endTimeUtc,
      e.background_url AS backgroundUrl
    FROM event e
    JOIN venue v ON v.id = e.venue_id
    LEFT JOIN room r ON r.id = e.room_id
    JOIN event_host h ON h.event_id = e.id
    WHERE h.party_id = #{hostId}
    ORDER BY e.start_time_utc DESC
    LIMIT #{limit} OFFSET #{offset}
  """)
    List<EventSummary> listByHost(@Param("hostId") Long hostId,
                                  @Param("limit") int limit,
                                  @Param("offset") int offset);

    @Select("""
    SELECT COUNT(*) FROM event e
    JOIN event_host h ON h.event_id = e.id
    WHERE h.party_id = #{hostId}
  """)
    long countByHost(@Param("hostId") Long hostId);

    @Select("""
    SELECT
      e.id, e.title, e.description,
      e.venue_id AS venueId, v.name AS venueName,
      e.room_id AS roomId, r.name AS roomName,
      e.timezone, e.offset_minutes AS offsetMinutes,
      e.start_time_local AS startTimeLocal, e.end_time_local AS endTimeLocal,
      e.start_time_utc AS startTimeUtc,   e.end_time_utc AS endTimeUtc,
      e.background_url AS backgroundUrl
    FROM event e
    JOIN venue v ON v.id = e.venue_id
    LEFT JOIN room r ON r.id = e.room_id
    WHERE e.venue_id = #{venueId}
    ORDER BY e.start_time_utc DESC
    LIMIT #{limit} OFFSET #{offset}
  """)
    List<EventSummary> listByVenue(@Param("venueId") Long venueId,
                                   @Param("limit") int limit,
                                   @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM event WHERE venue_id = #{venueId}")
    long countByVenue(@Param("venueId") Long venueId);

    // Inserts
    @Insert("""
    INSERT INTO event (
      parent_event_id, venue_id, room_id, title, description, background_url,
      start_time_local, end_time_local, timezone, offset_minutes,
      start_time_utc, end_time_utc
    ) VALUES (
      #{parentEventId}, #{venueId}, #{roomId}, #{title}, #{description}, #{backgroundUrl},
      #{startTimeLocal}, #{endTimeLocal}, #{timezone}, #{offsetMinutes},
      #{startTimeUtc}, #{endTimeUtc}
    )
  """)
    int insert(@Param("parentEventId") Long parentEventId,
               @Param("venueId") Long venueId,
               @Param("roomId") Long roomId,
               @Param("title") String title,
               @Param("description") String description,
               @Param("backgroundUrl") String backgroundUrl,
               @Param("startTimeLocal") LocalDateTime startTimeLocal,
               @Param("endTimeLocal") LocalDateTime endTimeLocal,
               @Param("timezone") String timezone,
               @Param("offsetMinutes") int offsetMinutes,
               @Param("startTimeUtc") Instant startTimeUtc,
               @Param("endTimeUtc") Instant endTimeUtc);

    @Select("SELECT LAST_INSERT_ID()")
    Long lastInsertId();

    // Hosts
    @Insert("""
    INSERT INTO event_host (event_id, party_id, role, sort_order)
    VALUES (#{eventId}, #{partyId}, #{role}, COALESCE(#{sortOrder},0))
    ON DUPLICATE KEY UPDATE role = VALUES(role), sort_order = VALUES(sortOrder)
  """)
    int upsertHost(@Param("eventId") Long eventId,
                   @Param("partyId") Long partyId,
                   @Param("role") String role,
                   @Param("sortOrder") Integer sortOrder);

    @Delete("DELETE FROM event_host WHERE event_id = #{eventId} AND party_id = #{partyId}")
    int unlinkHost(@Param("eventId") Long eventId, @Param("partyId") Long partyId);

    // Update / Delete
    @Update("""
    UPDATE event SET
      venue_id = #{venueId},
      room_id = #{roomId},
      title = #{title},
      description = #{description},
      background_url = #{backgroundUrl},
      start_time_local = #{startTimeLocal},
      end_time_local = #{endTimeLocal},
      timezone = #{timezone},
      offset_minutes = #{offsetMinutes},
      start_time_utc = #{startTimeUtc},
      end_time_utc = #{endTimeUtc}
    WHERE id = #{id}
  """)
    int update(@Param("id") Long id,
               @Param("venueId") Long venueId,
               @Param("roomId") Long roomId,
               @Param("title") String title,
               @Param("description") String description,
               @Param("backgroundUrl") String backgroundUrl,
               @Param("startTimeLocal") LocalDateTime startTimeLocal,
               @Param("endTimeLocal") LocalDateTime endTimeLocal,
               @Param("timezone") String timezone,
               @Param("offsetMinutes") int offsetMinutes,
               @Param("startTimeUtc") Instant startTimeUtc,
               @Param("endTimeUtc") Instant endTimeUtc);

    @Delete("DELETE FROM event WHERE id = #{id}")
    int delete(@Param("id") Long id);
}