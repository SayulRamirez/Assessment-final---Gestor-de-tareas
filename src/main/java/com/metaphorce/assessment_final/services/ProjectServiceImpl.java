package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.*;
import com.metaphorce.assessment_final.entities.Project;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.Status;
import com.metaphorce.assessment_final.enums.UserStatus;
import com.metaphorce.assessment_final.exceptions.EntityNotActiveException;
import com.metaphorce.assessment_final.repositories.ProjectRepository;
import com.metaphorce.assessment_final.repositories.TaskRepository;
import com.metaphorce.assessment_final.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

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

        Project project = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found whit id: " + id));

        return new ProjectResponse(project.getId(), project.getTitle(), project.getDescription(), project.getStatus(), project.getEstimatedCompletion());
    }

    @Override
    public List<ProjectResponse> getProjects(Long id) {

        List<Project> projects = projectRepository.findAllByLeaderId(id);

        if (projects.isEmpty()) throw new EntityNotFoundException("No projects were found with the id leader: " + id);

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

        project.setStatus(request.status());

        projectRepository.save(project);

        return new ProjectResponse(project.getId(), project.getTitle(), project.getDescription(), project.getStatus(), project.getEstimatedCompletion());
    }

    @Override
    public void delete(Long id) {

        taskRepository.deleteAllByProjectId(id);

        projectRepository.deleteById(id);
    }

    @Override
    public List<Report> getReport(Long id) {

        if (!projectRepository.existsById(id)) throw  new EntityNotFoundException("Project not found whit id: " + id);

        List<User> responsible = taskRepository.findResponsibleByProjectId(id);

        List<Report> reports = new ArrayList<>();

        if (!responsible.isEmpty()) {

            responsible.forEach(user -> {

                List<TaskStatusCount> taskStatusCounts = taskRepository.countTaskByStatus(user.getId());

                int pending = 0;
                int completed = 0;
                int inProgress = 0;
                int total = 0;

                if (!taskStatusCounts.isEmpty()) {

                    for (TaskStatusCount count : taskStatusCounts) {

                        switch (count.getStatus()) {
                            case PENDING -> pending = count.getCount();
                            case IN_PROGRESS -> inProgress = count.getCount();
                            case COMPLETE -> completed = count.getCount();
                        }

                        total += count.getCount();
                    }
                }

                reports.add(new Report(user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhoneNumber(), total, pending, inProgress, completed));
            });
        }

        return reports;
    }
}
