package com.darcsoftware.eventsapi.common.time;

import org.springframework.stereotype.Service;

import java.time.*;
import java.time.zone.ZoneRulesException;

@Service
public class TimezoneService {

    public record LocalUtcPair(Instant startUtc, Instant endUtc, int offsetMinutes) {}

    public LocalUtcPair projectToUtc(LocalDateTime startLocal, LocalDateTime endLocal, String timezone) {
        try {
            ZoneId zid = ZoneId.of(timezone);
            ZonedDateTime zStart = startLocal.atZone(zid);
            ZonedDateTime zEnd   = endLocal.atZone(zid);
            int offsetMinutes = zStart.getOffset().getTotalSeconds() / 60;
            return new LocalUtcPair(zStart.toInstant(), zEnd.toInstant(), offsetMinutes);
        } catch (ZoneRulesException e) {
            throw new IllegalArgumentException("Invalid timezone: " + timezone);
        }
    }
}