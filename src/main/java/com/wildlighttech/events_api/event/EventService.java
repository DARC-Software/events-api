package com.wildlighttech.events_api.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventMapper eventMapper;

    public List<Event> getEvents() { return this.eventMapper.getEvents(); }

    public Event getEventById(Long id) { return this.eventMapper.getEventById(id); }

    public Event getEventByVenueId(Long venueId) { return this.eventMapper.getEventByVenueId(venueId); }

    public void createEvent(Event event) { this.eventMapper.createEvent(event); }

    public void updateEvent(Event event) { this.eventMapper.updateEvent(event); }

    public void deleteEvent(Long id) { this.eventMapper.deleteEvent(id); }
}
