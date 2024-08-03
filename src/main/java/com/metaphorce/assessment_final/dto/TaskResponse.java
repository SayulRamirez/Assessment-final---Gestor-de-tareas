package com.metaphorce.assessment_final.dto;

import com.metaphorce.assessment_final.enums.Priority;
import com.metaphorce.assessment_final.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record TaskResponse (

        @Schema(example = "12")
        Long id,

        @Schema(example = "any title")
        String title,

        @Schema(example = "any description")
        String description,

        @Schema(example = "COMPLETE")
        Status status,

        @Schema(example = "2024-01-11")
        LocalDate estimate_delivery,

        @Schema(example = "HIGH")
        Priority priority,

        @Schema(example = "2024-01-02")
        LocalDate create_date,

        @Schema(example = "3")
        int runtime
){
}
