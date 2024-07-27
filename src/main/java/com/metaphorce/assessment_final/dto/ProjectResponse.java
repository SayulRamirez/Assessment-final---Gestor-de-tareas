package com.metaphorce.assessment_final.dto;

import com.metaphorce.assessment_final.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record ProjectResponse(

        @Schema(example = "12")
        Long id,

        @Schema(example = "any title")
        String title,

        @Schema(example = "any description")
        String description,

        @Schema(example = "COMPLETE")
        Status status,

        @Schema(example = "2025-12-03")
        LocalDate estimated_completion
) {
}
