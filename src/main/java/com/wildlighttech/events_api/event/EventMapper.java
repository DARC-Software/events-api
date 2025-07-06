package com.wildlighttech.events_api.event;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventMapper {
    @Select("SELECT * FROM event")
    List<Event> getEvents();

    @Select("SELECT * FROM event WHERE id = #{id}")
    Event getEventById(Long id);

    @Select("SELECT * FROM event WHERE venueId = #{venueId}")
    Event getEventByVenueId(Long venueId);

    @Insert("INSERT INTO event(name, startTime, endTime, venueId) VALUES(#{name}, #{startTime}, #{endTime}, #{venueId})")
    void createEvent(Event event);

    @Update("UPDATE event SET name=#{name}, startTime=#{startTime}, endTime=#{endTime} WHERE id=#{id}")
    void updateEvent(Event event);

    @Delete("DELETE FROM event WHERE id=#{id}")
    void deleteEvent(Long id);

}
