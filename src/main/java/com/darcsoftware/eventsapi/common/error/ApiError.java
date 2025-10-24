package com.darcsoftware.eventsapi.common.error;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "ApiError")
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {}