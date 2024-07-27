package com.metaphorce.assessment_final.dto;

import com.metaphorce.assessment_final.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(

        @Schema(example = "21")
        Long id,

        @Schema(example = "Juan")
        String first_name,

        @Schema(example = "Ramírez")
        String last_name,

        @Schema(example = "Mata")
        String maternal_surname,

        @Schema(example = "2344221243")
        String phone_number,

        @Schema(example = "ACTIVE")
        UserStatus status,

        @Schema(example = "juan@example.com")
        String email
) {
}
