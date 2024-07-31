package com.metaphorce.assessment_final.dto;


import java.util.List;

public record ReportResponse(

        ProjectResponse project,

        List<Report> report
) {
}
