package com.darcsoftware.events_api.event;

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
    private String type;
    private String host;
    private String room;
    private Long venueId;
}
