package com.darcsoftware.eventsapi.events;

import com.darcsoftware.eventsapi.events.dto.EventListResponse;
import com.darcsoftware.eventsapi.events.dto.EventSummary;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EventService {

    private final EventMapper eventMapper;

    public EventService(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public EventListResponse getByHost(
            long partyId,
            Instant fromUtc,
            Instant toUtc,
            boolean includePast,
            Integer limit,
            Integer offset,
            boolean includeTotal
    ) {
        Instant now = Instant.now();
        Instant from = (fromUtc != null) ? fromUtc : now;
        Instant to = (toUtc != null) ? toUtc : now.plus(90, ChronoUnit.DAYS);

        int l = (limit == null) ? 50 : Math.min(Math.max(limit, 1), 200);
        int o = (offset == null) ? 0 : Math.max(offset, 0);

        List<EventSummary> items = eventMapper.findEventsByHost(partyId, from, to, includePast, l, o);
        Long total = includeTotal ? eventMapper.countEventsByHost(partyId, from, to, includePast) : null;

        return new EventListResponse(items, l, o, total);
    }
}
