package com.darcsoftware.eventsapi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({
        "com.darcsoftware.eventsapi.venue",
        "com.darcsoftware.eventsapi.room",
        "com.darcsoftware.eventsapi.party",
        "com.darcsoftware.eventsapi.profiles.person",
        "com.darcsoftware.eventsapi.profiles.group"
})
public class MyBatisConfig {}