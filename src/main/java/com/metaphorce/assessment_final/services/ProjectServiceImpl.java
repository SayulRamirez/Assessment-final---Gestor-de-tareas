package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.ProjectRequest;
import com.metaphorce.assessment_final.dto.ProjectResponse;
import com.metaphorce.assessment_final.entities.Project;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.Status;
import com.metaphorce.assessment_final.enums.UserStatus;
import com.metaphorce.assessment_final.exceptions.EntityNotActiveException;
import com.metaphorce.assessment_final.exceptions.ResourceNotFound;
import com.metaphorce.assessment_final.repositories.ProjectRepository;
import com.metaphorce.assessment_final.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        User user = userRepository.findById(request.leader()).orElseThrow(() -> new EntityNotFoundException("User not found whit id: " + request.leader()));

        if (user.getStatus() != UserStatus.ACTIVE) throw new EntityNotActiveException("The user is blocked or deleted");

        Project project = projectRepository.save(Project.builder()
                .title(request.title())
                .description(request.description())
                .leader(user)
                .status(Status.PENDING)
                .estimatedCompletion(request.estimate_completion()).build());

        return new ProjectResponse(project.getId(), project.getTitle(), project.getDescription(),project.getStatus(), project.getEstimatedCompletion());
    }

    @Override
    public ProjectResponse getProject(Long id) {

        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Project not found whit id: " + id));

        return new ProjectResponse(project.getId(), project.getTitle(), project.getTitle(), project.getStatus(), project.getEstimatedCompletion());
    }

    @Override
    public List<ProjectResponse> getProjects(Long id) {

        List<Project> projects = projectRepository.findAllByLeaderId(id);

        if (projects.isEmpty()) throw new ResourceNotFound("No projects were found with the id leader: " + id);

        return projects.stream().map(project -> new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStatus(),
                project.getEstimatedCompletion())).toList();
    }

    @Override
    public ProjectResponse changeStatus(ChangeStatusRequest request) {

        Project project = projectRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Project not found whit id: " + request.id()));

        projectRepository.save(project);

        return new ProjectResponse(project.getId(), project.getTitle(), project.getTitle(), project.getStatus(), project.getEstimatedCompletion());
    }
}
