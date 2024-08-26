package com.metaphorce.assessment_final.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateInfoRequest(

        @Schema(example = "1")
        @NotNull(message = "The parameter must not be null")
        @Positive(message = "The parameter must be greater 0")
        Long id,

        @Schema(example = "Alvaro")
        @NotBlank(message = "The parameter must not be null or blank")
        String first_name,

        @Schema(example = "Peréz")
        @NotBlank(message = "The parameter must not be null or blank")
        String last_name,

        @Schema(example = "Apellido materno (opcional)")
        @NotNull(message = "The field not must be null")
        String maternal_surname,

        @Schema(example = "47712345432")
        @NotBlank(message = "The parameter must not be null or blank")
        @Size(min = 10, max = 15, message = "The parameter must not be greater that 15 or minor that 10")
        String phone_number
) {
}
