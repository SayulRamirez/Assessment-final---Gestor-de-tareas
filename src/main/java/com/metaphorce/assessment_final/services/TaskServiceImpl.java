package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.ProjectResponse;
import com.metaphorce.assessment_final.dto.TaskRequest;
import com.metaphorce.assessment_final.dto.TaskResponse;
import com.metaphorce.assessment_final.entities.Project;
import com.metaphorce.assessment_final.entities.Task;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.Status;
import com.metaphorce.assessment_final.repositories.ProjectRepository;
import com.metaphorce.assessment_final.repositories.TaskRepository;
import com.metaphorce.assessment_final.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final TaskRepository  taskRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {

        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new EntityNotFoundException("User not found whit email " + request.email()));

        Project project = projectRepository.findById(request.project()).orElseThrow(() -> new EntityNotFoundException("Project not found whit id " + request.project()));

        Task task = taskRepository.save(Task.builder().title(request.title())
                .description(request.description())
                .responsible(user)
                .status(Status.PENDING)
                .project(project)
                .estimatedDelivery(request.estimate_delivery())
                .priority(request.priority()).build());

        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                new ProjectResponse(task.getProject().getId(),
                        task.getProject().getTitle(),
                        task.getProject().getDescription(),
                        task.getProject().getStatus(),
                        task.getProject().getEstimatedCompletion()),
                task.getEstimatedDelivery(),
                task.getPriority());
    }

    @Override
    public TaskResponse getTask(Long id) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found task whit id " + id));

        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                new ProjectResponse(task.getProject().getId(),
                        task.getProject().getTitle(),
                        task.getProject().getDescription(),
                        task.getProject().getStatus(),
                        task.getProject().getEstimatedCompletion()),
                task.getEstimatedDelivery(),
                task.getPriority());
    }

    @Override
    public List<TaskResponse> getTasks(Long id) {

        List<Task> tasks = taskRepository.findAllByResponsibleId(id);

        if (tasks.isEmpty()) throw new EntityNotFoundException("No projects were found with the id leader: " + id);

        return tasks.stream().map(task -> new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                new ProjectResponse(task.getProject().getId(),
                        task.getProject().getTitle(),
                        task.getProject().getDescription(),
                        task.getProject().getStatus(),
                        task.getProject().getEstimatedCompletion()),
                task.getEstimatedDelivery(),
                task.getPriority())).toList();
    }

    @Override
    public TaskResponse changeStatus(ChangeStatusRequest request) {

        Task find = taskRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Not found task whit id " + request.id()));

        find.setStatus(request.status());

        Task task = taskRepository.save(find);

        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                new ProjectResponse(task.getProject().getId(),
                        task.getProject().getTitle(),
                        task.getProject().getDescription(),
                        task.getProject().getStatus(),
                        task.getProject().getEstimatedCompletion()),
                task.getEstimatedDelivery(),
                task.getPriority());
    }

    @Override
    public void delete(Long id) {taskRepository.deleteById(id);}
}
