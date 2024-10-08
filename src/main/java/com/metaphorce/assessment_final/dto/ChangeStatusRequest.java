package com.metaphorce.assessment_final.dto;

import com.metaphorce.assessment_final.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequest (
        @Schema(example = "1")
        @NotNull
        Long id,

        @Schema(example = "IN_PROGRESS")
        @NotNull
        Status status
){

}
