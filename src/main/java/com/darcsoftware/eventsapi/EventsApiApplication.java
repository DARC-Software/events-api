package com.darcsoftware.eventsapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {
		"com.darcsoftware.eventsapi.event",
		"com.darcsoftware.eventsapi.party",
		"com.darcsoftware.eventsapi.room",
		"com.darcsoftware.eventsapi.venue",
		"com.darcsoftware.eventsapi.profiles"
})
public class EventsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsApiApplication.class, args);
	}

}
