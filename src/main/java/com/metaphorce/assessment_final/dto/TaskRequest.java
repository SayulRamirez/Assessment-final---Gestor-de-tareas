package com.metaphorce.assessment_final.dto;

import com.metaphorce.assessment_final.enums.Priority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRequest (

        @Schema(example = "any title")
        @NotBlank(message = "The title must not be null or blank")
        String title,

        @Schema(example = "any description")
        @NotBlank(message = "The description must not be null or blank")
        String description,

        @Schema(example = "email@example.com")
        @NotNull(message = "The email must not be null")
        @Email(message = "Must be email")
        String email,

        @Schema(example = "12")
        @NotNull(message = "The id project must not be null")
        Long project,

        @Schema(example = "2024-01-11")
        @NotNull(message = "The estimate delivery date must not be null")
        LocalDate estimate_delivery,

        @Schema(example = "MEDIUM")
        @NotNull(message = "The priority must not be null")
        Priority priority
) {
}
