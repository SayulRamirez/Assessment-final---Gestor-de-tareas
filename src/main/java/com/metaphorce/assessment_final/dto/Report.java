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

        @Schema(example = "10")
        int assigned,

        @Schema(example = "3")
        int pending,

        @Schema(example = "2")
        int in_progress,

        @Schema(example = "5")
        int complete
) {
}
