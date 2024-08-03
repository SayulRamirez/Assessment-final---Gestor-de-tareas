package com.metaphorce.assessment_final.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponse (

        @Schema(example = "token secret")
        String token
){
}
