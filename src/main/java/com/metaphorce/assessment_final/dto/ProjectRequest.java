package com.metaphorce.assessment_final.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjectRequest(

        @Schema(example = "Any title")
        @Size(min = 1, max = 150, message = "Min 1 character and max 150")
        @NotBlank(message = "The title must not be blank or null")
        String title,

        @Schema(example = "Any description")
        @NotBlank(message = "The description must not be blank or null")
        String description,

        @Schema(example = "12")
        @NotNull(message = "The field must no be null")
        Long leader,

        @Schema(example = "2007-12-03")
        @NotNull(message = "The estimate completion must not be null or blank")
        LocalDate estimate_completion
) {
}
