package com.metaphorce.assessment_final.dto;

import com.metaphorce.assessment_final.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record StatusUserRequest(

        @Schema(example = "1")
        @NotNull
        Long id,

        @Schema(example = "BLOCKED")
        @NotNull
        UserStatus status
) {
}
