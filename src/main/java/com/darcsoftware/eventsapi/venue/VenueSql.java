package com.darcsoftware.eventsapi.venue;

import com.darcsoftware.eventsapi.venue.dto.VenueUpdateRequest;

public class VenueSql {

    public static String updateVenue() {
        // Dynamic update; only non-null fields are set. Slug is always passed (kept or new).
        return """
            <script>
            UPDATE venue
            <set>
              <if test="slug != null">slug = #{slug},</if>
              <if test="req.name != null">name = #{req.name},</if>
              <if test="req.addressLine1 != null">address_line1 = #{req.addressLine1},</if>
              <if test="req.addressLine2 != null">address_line2 = #{req.addressLine2},</if>
              <if test="req.city != null">city = #{req.city},</if>
              <if test="req.state != null">state = #{req.state},</if>
              <if test="req.zipCode != null">zip_code = #{req.zipCode},</if>
            </set>
            WHERE id = #{id}
            </script>
        """;
    }
}