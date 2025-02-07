package com.wildlighttech.barsync_events_api.venue;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VenueMapper {
    @Select("SELECT * FROM venues WHERE id = #{id}")
    Venue getVenueById(Long id);

    @Select("SELECT * FROM venues")
    List<Venue> getVenues();

    @Insert("INSERT INTO venues(name, address, phoneNumber) VALUES(#{name}, #{address}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createVenue(Venue venue);

    @Update("UPDATE venues SET name=#{name}, address=#{address}, phoneNumber=#{phoneNumber} WHERE id=#{id}")
    void updateVenue(Venue venue);

    @Delete("DELETE FROM venues WHERE id = #{id}")
    void deleteVenue(Long id);
}
