package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.ProjectRequest;
import com.metaphorce.assessment_final.dto.ProjectResponse;
import com.metaphorce.assessment_final.dto.ReportResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse getProject(Long id);

    List<ProjectResponse> getProjects(Long id);

    ProjectResponse changeStatus(ChangeStatusRequest request);

    void delete(Long id);

    ReportResponse getReport(Long id);
}
