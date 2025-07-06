package com.darcsoftware.events_api.venue;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VenueMapper {
    @Select("SELECT * FROM venue")
    List<Venue> getVenues();

    @Select("SELECT * FROM venue WHERE id = #{id}")
    Venue getVenueById(Long id);

    @Insert("INSERT INTO venue(name, address, phoneNumber) VALUES(#{name}, #{address}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createVenue(Venue venue);

    @Update("UPDATE venue SET name=#{name}, address=#{address}, phoneNumber=#{phoneNumber} WHERE id=#{id}")
    void updateVenue(Venue venue);

    @Delete("DELETE FROM venue WHERE id = #{id}")
    void deleteVenue(Long id);
}