package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.TaskRequest;
import com.metaphorce.assessment_final.dto.TaskResponse;
import com.metaphorce.assessment_final.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "create a new task", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest request) {

        TaskResponse task = taskService.createTask(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.id()).toUri();

        return ResponseEntity.created(location).body(task);
    }

    @Operation(summary = "Get task specific", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.getTask(id));
    }

    @Operation(summary = "Get all task", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.getTasks(id));
    }

    @Operation(summary = "changed task status (PENDING, IN_PROGRESS, COMPLETE)", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<TaskResponse> changeStatus(@RequestBody @Valid ChangeStatusRequest request) {
        return ResponseEntity.ok(taskService.changeStatus(request));
    }

    @Operation(summary = "Delete task by id", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        taskService.delete(id);

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all tasks from project by user", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "user/{idUser}/project/{idProject}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long idUser, @PathVariable Long idProject) {

        return ResponseEntity.ok(taskService.listUserTasksInProject(idUser, idProject));
    }
}
