package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.TaskRequest;
import com.metaphorce.assessment_final.dto.TaskResponse;
import com.metaphorce.assessment_final.entities.Project;
import com.metaphorce.assessment_final.entities.Task;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.Priority;
import com.metaphorce.assessment_final.enums.Status;
import com.metaphorce.assessment_final.repositories.ProjectRepository;
import com.metaphorce.assessment_final.repositories.TaskRepository;
import com.metaphorce.assessment_final.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl underTest;

    //createTask()
    @Test
    void whenCreateTaskUserNotExits() {

        TaskRequest request = new TaskRequest(null, null, "example@example.com", 1L, null, null);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> underTest.createTask(request));
    }

    @Test
    void whenCreateTaskProjectNotExists() {

        TaskRequest request = new TaskRequest(null, null, "example@example.com", 1L, null, null);

        User user = User.builder().build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> underTest.createTask(request));
    }

    @Test
    void whenCreateTaskIsSuccessful() {

        TaskRequest request = new TaskRequest("any title",
                "any description",
                "example@example.com",
                1L,
                LocalDate.now().plusMonths(1),
                Priority.LOW);

        User user = User.builder().id(1L).build();

        Project project = Project.builder()
                .id(2L)
                .title("any title")
                .description("any description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(3)).build();

        Task task = Task.builder()
                .id(1L)
                .title(request.title())
                .description(request.description())
                .responsible(user)
                .estimatedDelivery(request.estimate_delivery())
                .priority(request.priority())
                .project(project).build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse result = underTest.createTask(request);

        verify(taskRepository, times(1)).save(any(Task.class));
        assertTrue(result.id() > 0);
        assertEquals(request.title(), result.title());
    }

    //getTask()
    @Test
    void whenGetTaskIsEmpty() {

        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.getTask(1L));
    }

    @Test
    void whenGetTaskIsSuccessful() {

        Project project = Project.builder()
                .id(2L)
                .title("any title")
                .description("any description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(3)).build();

        Task task = Task.builder()
                .id(1L)
                .title("any title")
                .description("any description")
                .responsible(User.builder().id(1L).build())
                .estimatedDelivery(LocalDate.now())
                .priority(Priority.MEDIUM)
                .project(project).build();

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        TaskResponse result = underTest.getTask(task.getId());

        assertEquals(task.getId(), result.id());
        assertEquals(task.getDescription(), result.description());
        assertEquals(task.getStatus(), result.status());
    }

    //getTasks()
    @Test
    void whenGetTasksIsEmpty() {
        when(taskRepository.findAllByResponsibleId(anyLong())).thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> underTest.getTasks(1L));
    }

    @Test
    void whenGetTasksIsSuccessful() {

        Project project = Project.builder()
                .id(2L)
                .title("any title")
                .description("any description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(3)).build();

        Task task = Task.builder()
                .id(1L)
                .title("any title")
                .description("any description")
                .responsible(User.builder().id(1L).build())
                .estimatedDelivery(LocalDate.now())
                .priority(Priority.MEDIUM)
                .project(project).build();

        Task task2 = Task.builder()
                .id(1L)
                .title("any title")
                .description("any description")
                .responsible(User.builder().id(1L).build())
                .estimatedDelivery(LocalDate.now())
                .priority(Priority.MEDIUM)
                .project(project).build();

        when(taskRepository.findAllByResponsibleId(task.getId())).thenReturn(List.of(task, task2));

        List<TaskResponse> result = underTest.getTasks(task.getId());

        assertEquals(2, result.size());
    }

    //changedStatus()
    @Test
    void whenChangeStatusTaskNotExists() {

        ChangeStatusRequest request = new ChangeStatusRequest(1L, Status.PENDING);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> underTest.changeStatus(request));
    }

    @Test
    void whenChangeStatusIsSuccessful() {

        ChangeStatusRequest request = new ChangeStatusRequest(1L, Status.COMPLETE);

        Project project = Project.builder()
                .id(2L)
                .title("any title")
                .description("any description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(3)).build();

        Task task = Task.builder()
                .id(1L)
                .title("any title")
                .description("any description")
                .responsible(User.builder().id(1L).build())
                .estimatedDelivery(LocalDate.now())
                .priority(Priority.MEDIUM)
                .project(project).build();

        when(taskRepository.findById(request.id())).thenReturn(Optional.of(task));

        task.setStatus(request.status());
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse result = underTest.changeStatus(request);

        verify(taskRepository, times(1)).save(any(Task.class));
        assertEquals(request.status(), result.status());

    }

    //delete()
    @Test
    void whenDeleteTask() {

        underTest.delete(1L);

        verify(taskRepository, times(1)).deleteById(anyLong());
    }
}
