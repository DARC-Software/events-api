package com.darcsoftware.eventsapi.events;

import com.darcsoftware.eventsapi.events.dto.EventSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.time.Instant;
import java.util.List;

@Mapper
public interface EventMapper {
    @Select("""
        SELECT
            e.id,
            e.title,
            e.description,
            e.venue_id AS venueId,
            v.name AS venueName,
            e.room_id AS roomId,
            r.name AS roomName,
            e.timezone AS timezone,
            e.offset_minutes AS offsetMinutes,
            e.start_time_local AS startTimeLocal,
            e.end_time_local AS endTimeLocal,
            e.start_time_utc AS startTimeUtc,
            e.end_time_utc AS endTimeUtc
        FROM event e
        JOIN event_host eh ON eh.event_id = e.id
        JOIN venue v ON v.id = e.venue_id
        LEFT JOIN room r ON r.id = e.room_id
        WHERE eh.party_id = #{partyId}
            AND (
                #{includePast} = TRUE
                OR e.start_time_utc >= #{fromUtc}
            )
            AND (#{toUtc} IS NULL OR e.start_time_utc <= #{toUtc})
        ORDER BY e.start_time_utc ASC, e.id ASC
        LIMIT #{limit} OFFSET #{offset}
    """)
    @ResultType(EventSummary.class)
    List<EventSummary> findEventsByHost(
            @Param("partyId") long partyId,
            @Param("fromUtc") Instant fromUtc,
            @Param("toUtc") Instant toUtc,
            @Param("includePast") boolean includePast,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Select("""
        SELECT COUNT(*)
        FROM event e
        JOIN event_host eh ON eh.event_id = e.id
        WHERE eh.party_id = #{partyId}
            AND (
                #{includePast} = TRUE
                OR e.start_time_utc >= #{fromUtc}
            )
            AND (#{toUtc} IS NULL OR e.start_time_utc <= #{toUtc})
    """)
    long countEventsByHost(
            @Param("partyId") long partyId,
            @Param("fromUtc") Instant fromUtc,
            @Param("toUtc") Instant toUtc,
            @Param("includePast") boolean includePast
    );
}
