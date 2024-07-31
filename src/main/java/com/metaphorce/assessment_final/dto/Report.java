package com.metaphorce.assessment_final.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record Report(

        @Schema(example = "Juan")
        String first_name,

        @Schema(example = "Ramirez")
        String last_name,

        @Schema(example = "juan@example.com")
        String email,

        @Schema(example = "4332121544")
        String phone_number,

        @Schema(example = "3")
        long assigned,

        @Schema(example = "3")
        long pending,

        @Schema(example = "3")
        long in_progress,

        @Schema(example = "3")
        long complete
) {
}
