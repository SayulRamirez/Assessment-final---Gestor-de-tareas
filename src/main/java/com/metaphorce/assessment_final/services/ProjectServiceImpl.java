package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.*;
import com.metaphorce.assessment_final.entities.Project;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.Status;
import com.metaphorce.assessment_final.exceptions.EntityNotActiveException;
import com.metaphorce.assessment_final.repositories.ProjectRepository;
import com.metaphorce.assessment_final.repositories.TaskRepository;
import com.metaphorce.assessment_final.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        User user = userRepository.findById(request.leader()).orElseThrow(() -> {
            LOGGER.error("User not found with id: {}", request.leader());
            return new EntityNotFoundException("User not found whit id: " + request.leader());
        });

        if (!user.getStatus().getStatus()) {
            LOGGER.error("User is not active");
            throw new EntityNotActiveException("The user is blocked or deleted");
        }

        Project project = projectRepository.save(Project.builder()
                .title(request.title())
                .description(request.description())
                .leader(user)
                .status(Status.PENDING)
                .estimatedCompletion(request.estimate_completion()).build());

        return getResponse(project);
    }

    @Override
    public ProjectResponse getProject(Long id) {

        Project project = findProject(id);

        return getResponse(project);
    }

    @Override
    public List<ProjectResponse> getProjects(Long id) {

        return projectRepository.findAllByLeaderId(id)
                .stream()
                .map(ProjectServiceImpl::getResponse)
                .sorted(Comparator.comparing(ProjectResponse::status, Status::compareTo))
                .toList();
    }

    @Override
    public ProjectResponse changeStatus(ChangeStatusRequest request) {

        Project project = findProject(request.id());

        project.setStatus(request.status());

        return getResponse(projectRepository.save(project));
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Report> getReport(Long id) {

        if (!projectRepository.existsById(id)) throw new EntityNotFoundException("Project not found whit id: " + id);

        List<User> responsible = taskRepository.findResponsibleByProjectId(id);

        return responsible.stream().map(user -> {
            List<TaskStatusCount> taskStatusCounts = taskRepository.countTaskByStatus(user.getId());

            int pending = extractStatus(taskStatusCounts, TaskStatusCount::isPending);

            int inProgress = extractStatus(taskStatusCounts, TaskStatusCount::isInProgress);

            int completed = extractStatus(taskStatusCounts, TaskStatusCount::isCompleted);

            int total = taskStatusCounts.stream().mapToInt(TaskStatusCount::getCount).sum();

            return new Report(user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPhoneNumber(), total, pending, inProgress, completed);
        }).collect(Collectors.toList());
    }

    private int extractStatus(List<TaskStatusCount> tasksStatusCount, Predicate<TaskStatusCount> validation) {
        return tasksStatusCount.stream()
                .filter(validation)
                .mapToInt(TaskStatusCount::getCount)
                .sum();
    }

    private static ProjectResponse getResponse(Project project) {
        return new ProjectResponse(project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStatus(),
                project.getEstimatedCompletion());
    }

    private Project findProject(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Project not found with id: {}", id);
            return new EntityNotFoundException("Project not found whit id: " + id);
        });
    }
}
