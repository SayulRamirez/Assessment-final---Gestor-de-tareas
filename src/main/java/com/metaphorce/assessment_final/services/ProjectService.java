package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.ProjectRequest;
import com.metaphorce.assessment_final.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse getProject(Long id);

    List<ProjectResponse> getProjects(Long id);

    ProjectResponse changeStatus(ChangeStatusRequest request);
}
