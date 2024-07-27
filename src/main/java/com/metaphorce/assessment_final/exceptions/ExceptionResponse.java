package com.metaphorce.assessment_final.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ExceptionResponse(

        @Schema(example = "User not found")
        String message,

        @Schema(example = "2024-07-26T23:48:13.0317694-06:00")
        ZonedDateTime timestamp,

        @Schema(example = "NOT_FOUND")
        HttpStatus status
) {}
