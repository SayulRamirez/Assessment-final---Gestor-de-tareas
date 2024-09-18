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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final TaskRepository  taskRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public TaskResponse createTask(TaskRequest request) {

        User user = userRepository.findByEmailAndIsActive(request.email())
                .orElseThrow(() -> {
                    LOGGER.error("User not found");
                    return new EntityNotFoundException("User not found or blocked or delete");
                });

        Project project = projectRepository.findById(request.project())
                .orElseThrow(() -> {
                    LOGGER.error("Project not found");
                    return new EntityNotFoundException("Project not found whit id " + request.project());
                });

        Task task = taskRepository.save(Task.builder()
                .title(request.title())
                .description(request.description())
                .responsible(user)
                .status(Status.PENDING)
                .project(project)
                .estimatedDelivery(request.estimate_delivery())
                .priority(request.priority())
                .createDate(LocalDate.now())
                .runtime(0).build());

        return getResponse(task);
    }

    @Override
    public TaskResponse getTask(Long id) {

        Task task = findTask(id);

        return getResponse(task);
    }

    @Override
    public List<TaskResponse> getTasks(Long id) {

        return taskRepository.findAllByResponsibleId(id).stream()
                .map(TaskServiceImpl::getResponse)
                .sorted(Comparator
                        .comparing(TaskResponse::priority, Enum::compareTo))
                .toList();
    }

    @Override
    public TaskResponse changeStatus(ChangeStatusRequest request) {

        Task task = findTask(request.id());

        task.setStatus(request.status());

        if (request.status() == Status.COMPLETE) {
            task.setRuntime((int) ChronoUnit.DAYS.between(task.getCreateDate(), LocalDate.now()));
        }

        return getResponse(taskRepository.save(task));
    }

    @Override
    public void delete(Long id) {taskRepository.deleteById(id);}

    @Override
    public List<TaskResponse> listUserTasksInProject(Long idUser, Long idProject) {

        List<Task> tasks = taskRepository.findAllByResponsibleIdAndProjectId(idUser, idProject);

        return tasks.stream()
                .map(TaskServiceImpl::getResponse)
                .sorted(Comparator.comparing(TaskResponse::estimate_delivery, LocalDate::compareTo)
                        .reversed())
                .toList();
    }

    private Task findTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Task not found");
            return new EntityNotFoundException("Not found task whit id " + id);
        });
    }

    private static TaskResponse getResponse(Task task) {
        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getEstimatedDelivery(),
                task.getPriority(),
                task.getCreateDate(),
                task.getRuntime());
    }
}
