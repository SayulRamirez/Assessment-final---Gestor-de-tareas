package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.*;
import com.metaphorce.assessment_final.entities.Project;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.Status;
import com.metaphorce.assessment_final.enums.UserStatus;
import com.metaphorce.assessment_final.exceptions.EntityNotActiveException;
import com.metaphorce.assessment_final.exceptions.ResourceNotFound;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectServiceImpl underTest;

    //createProject()
    @Test
    void whenCreateProjectLeaderNotExists() {

        ProjectRequest request = new ProjectRequest("any title", "any description", 1L, LocalDate.now());

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.createProject(request));
    }

    @Test
    void whenCreateProjectLeaderIsBlocked() {

        ProjectRequest request = new ProjectRequest("any title", "any description", 1L, LocalDate.now());

        User user = User.builder().id(1L).status(UserStatus.BLOCKED).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(EntityNotActiveException.class, () -> underTest.createProject(request));
    }

    @Test
    void whenCreateProjectLeaderIsDeleted() {
        ProjectRequest request = new ProjectRequest("any title", "any description", 1L, LocalDate.now());

        User user = User.builder().id(1L).status(UserStatus.DELETED).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(EntityNotActiveException.class, () -> underTest.createProject(request));
    }

    @Test
    void whenCreateProjectIsSuccessful() {

        ProjectRequest request = new ProjectRequest("any title", "any description", 1L, LocalDate.now());

        User user = User.builder().id(1L).status(UserStatus.ACTIVE).build();

        Project project = Project.builder()
                .id(1L)
                .title(request.title())
                .description(request.description())
                .leader(user)
                .estimatedCompletion(request.estimate_completion()).build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectResponse result = underTest.createProject(request);

        assertTrue(result.id() > 0);
        assertEquals(request.title(), result.title());
        assertEquals(request.description(), result.description());
        verify(projectRepository, timeout(1)).save(any(Project.class));
    }

    //getProject()
    @Test
    void whenGetProjectIsEmpty() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> underTest.getProject(1L));
    }

    @Test
    void whenGetProjectIsSuccessful() {

        Project project = Project.builder().id(1L)
                .title("first title")
                .description("first description")
                .status(Status.IN_PROGRESS)
                .estimatedCompletion(LocalDate.now().plusDays(3)).build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        ProjectResponse result = underTest.getProject(1L);

        assertEquals(project.getId(), result.id());
        assertEquals(project.getTitle(), result.title());
    }

    //getProjects()
    @Test
    void whenGetProjectsIsEmpty() {

        when(projectRepository.findAllByLeaderId(anyLong())).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFound.class, () -> underTest.getProjects(1L));
    }

    @Test
    void whenGetProjectsIsSuccessful() {

        Project project1 = Project.builder()
                .id(1L)
                .title("first title")
                .description("first description")
                .status(Status.IN_PROGRESS)
                .estimatedCompletion(LocalDate.now().plusDays(3)).build();

        Project project2 = Project.builder()
                .id(5L)
                .title("second title")
                .description("second description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(6)).build();

        List<Project> responses = List.of(project1, project2);

        when(projectRepository.findAllByLeaderId(anyLong())).thenReturn(responses);

        List<ProjectResponse> result = underTest.getProjects(1L);

        assertEquals(2, result.size());
    }

    //changeStatus()
    @Test
    void whenChangeStatusProjectNotFound() {

        ChangeStatusRequest request = new ChangeStatusRequest(1L, Status.COMPLETE);

        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.changeStatus(request));
    }

    @Test
    void whenChangeStatusIsSuccessful() {

        ChangeStatusRequest request = new ChangeStatusRequest(1L, Status.COMPLETE);

        Project project = Project.builder()
                .id(request.id())
                .title("second title")
                .description("second description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(6)).build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        project.setStatus(request.status());

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectResponse result = underTest.changeStatus(request);

        assertEquals(request.id(), result.id());
        assertEquals(request.status(), result.status());
        verify(projectRepository, timeout(1)).save(any(Project.class));
    }

    //delete()
    @Test
    void whenDeleteProject() {

        underTest.delete(1L);

        verify(taskRepository, times(1)).deleteAllByProjectId(anyLong());
        verify(projectRepository, times(1)).deleteById(anyLong());
    }

    //getReport()
    @Test
    void whenGetReportProjectNotFound() {

        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> underTest.getReport(1L));
    }

    @Test
    void whenGetReportNoResponsible() {
        Project project = Project.builder()
                .id(1L)
                .title("second title")
                .description("second description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(6)).build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        when(taskRepository.findResponsibleByProjectId(anyLong())).thenReturn(new ArrayList<>());

        ReportResponse result = underTest.getReport(1L);

        assertEquals(project.getTitle(), result.project().title());
        assertEquals(project.getStatus(), result.project().status());
        assertTrue(result.report().isEmpty());
    }

    @Test
    void whenGetProjectNoTasksAssigned() {
        Project project = Project.builder()
                .id(1L)
                .title("second title")
                .description("second description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(6)).build();

        User user = User.builder().id(1L)
                .firstName("juan")
                .lastName("martinez")
                .email("juan@example.com")
                .phoneNumber("1232121432").build();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        when(taskRepository.findResponsibleByProjectId(anyLong())).thenReturn(List.of(user));

        when(taskRepository.countTaskByStatus(user.getId())).thenReturn(new ArrayList<>());

        ReportResponse result = underTest.getReport(1L);

        assertEquals(project.getTitle(), result.project().title());
        assertEquals(project.getStatus(), result.project().status());
        assertFalse(result.report().isEmpty());

        result.report().forEach(report -> {
            assertEquals(0, report.assigned());
            assertEquals(0, report.pending());
            assertEquals(0, report.in_progress());
            assertEquals(0, report.complete());
        });
    }

    @Test
    void whenGetProjectTasksAssigned() {
        Project project = Project.builder()
                .id(1L)
                .title("second title")
                .description("second description")
                .status(Status.PENDING)
                .estimatedCompletion(LocalDate.now().plusDays(6)).build();

        User user = User.builder().id(1L)
                .firstName("juan")
                .lastName("martinez")
                .email("juan@example.com")
                .phoneNumber("1232121432").build();

        TaskStatusCount count = new TaskStatusCount() {
            @Override
            public Status getStatus() {
                return Status.PENDING;
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        TaskStatusCount count2 = new TaskStatusCount() {
            @Override
            public Status getStatus() {
                return Status.COMPLETE;
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        when(taskRepository.findResponsibleByProjectId(anyLong())).thenReturn(List.of(user));

        when(taskRepository.countTaskByStatus(user.getId())).thenReturn(List.of(count, count2));

        ReportResponse result = underTest.getReport(1L);

        assertEquals(project.getTitle(), result.project().title());
        assertEquals(project.getStatus(), result.project().status());
        assertFalse(result.report().isEmpty());

        result.report().forEach(report -> {
            assertEquals(6, report.assigned());
            assertEquals(2, report.pending());
            assertEquals(0, report.in_progress());
            assertEquals(4, report.complete());
        });
    }
}
