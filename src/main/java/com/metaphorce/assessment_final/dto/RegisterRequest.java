package com.metaphorce.assessment_final.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest (

        @Schema(example = "juan")
        @NotBlank(message = "Fisrt name must not be null or blank")
        @Size(min = 1, max = 50, message = "Min 1 and max 50 characters")
        String first_name,

        @Schema(example = "perez")
        @NotBlank(message = "last name must not be null or blank")
        @Size(min = 1, max = 30, message = "Min 1 and max 30 characters")
        String last_name,

        @Schema(example = "meza")
        @Size(min = 1, max = 30, message = "Min 1 and max 30 characters")
        String maternal_surname,

        @Schema(example = "4772352465")
        @NotBlank(message = "phone number must not be null or blank")
        @Size(min = 10, max = 15, message = "Min 10 and max 15 characters")
        String phone_number,

        @Schema(example = "example@example.com")
        @NotNull(message = "Must not be null")
        @Email(message = "input email")
        String email,

        @Schema(example = "secret password")
        @NotBlank(message = "Must not be null")
        String password
){}
