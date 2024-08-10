package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final TaskRepository  taskRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {

        User user = userRepository.findByEmailAndIsActive(request.email()).orElseThrow(() -> new EntityNotFoundException("User not found or blocked or delete"));

        Project project = projectRepository.findById(request.project()).orElseThrow(() -> new EntityNotFoundException("Project not found whit id " + request.project()));

        Task task = taskRepository.save(Task.builder().title(request.title())
                .description(request.description())
                .responsible(user)
                .status(Status.PENDING)
                .project(project)
                .estimatedDelivery(request.estimate_delivery())
                .priority(request.priority())
                .createDate(LocalDate.now())
                .runtime(0).build());

        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getEstimatedDelivery(),
                task.getPriority(),
                task.getCreateDate(),
                task.getRuntime());
    }

    @Override
    public TaskResponse getTask(Long id) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found task whit id " + id));

        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getEstimatedDelivery(),
                task.getPriority(),
                task.getCreateDate(),
                task.getRuntime());
    }

    @Override
    public List<TaskResponse> getTasks(Long id) {

        List<Task> tasks = taskRepository.findAllByResponsibleId(id);

        if (tasks.isEmpty()) throw new EntityNotFoundException("No projects were found with the id leader: " + id);

        return tasks.stream().map(task -> new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getEstimatedDelivery(),
                task.getPriority(),
                task.getCreateDate(),
                task.getRuntime())).toList();
    }

    @Override
    public TaskResponse changeStatus(ChangeStatusRequest request) {

        Task find = taskRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Not found task whit id " + request.id()));

        find.setStatus(request.status());

        if (request.status() == Status.COMPLETE) {

            find.setRuntime((int) ChronoUnit.DAYS.between(find.getCreateDate(), LocalDate.now()));
        }

        Task task = taskRepository.save(find);

        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getEstimatedDelivery(),
                task.getPriority(),
                task.getCreateDate(),
                task.getRuntime());
    }

    @Override
    public void delete(Long id) {taskRepository.deleteById(id);}

    @Override
    public List<TaskResponse> listUserTasksInProject(Long idUser, Long idProject) {

        List<Task> tasks = taskRepository.findAllByResponsibleIdAndProjectId(idUser, idProject);

        if (tasks.isEmpty()) throw new EntityNotFoundException("Task not found to user: " + idUser);

        return tasks.stream().map(task -> new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getEstimatedDelivery(),
                task.getPriority(),
                task.getCreateDate(),
                task.getRuntime())).toList();
    }
}
