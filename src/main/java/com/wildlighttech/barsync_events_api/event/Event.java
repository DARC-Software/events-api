package com.wildlighttech.barsync_events_api.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Event {
    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Long venueId;
}
