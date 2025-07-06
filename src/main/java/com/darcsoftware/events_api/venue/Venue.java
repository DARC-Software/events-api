package com.darcsoftware.events_api.venue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Venue {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
}
