package com.metaphorce.assessment_final.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest (

        @Schema(example = "example@example.com")
        @NotNull(message = "Must not be null")
        @Email(message = "input email")
        String email,

        @Schema(example = "secretpassword")
        @NotBlank(message = "Must not be null")
        String password
){
}
