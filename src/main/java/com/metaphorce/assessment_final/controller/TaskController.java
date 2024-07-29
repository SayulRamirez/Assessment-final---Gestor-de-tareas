package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.TaskRequest;
import com.metaphorce.assessment_final.dto.TaskResponse;
import com.metaphorce.assessment_final.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "create a new task")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<TaskResponse> create(TaskRequest request) {

        TaskResponse task = taskService.createTask(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.id()).toUri();

        return ResponseEntity.created(location).body(task);
    }

    @Operation(summary = "Get task specific")
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.getTask(id));
    }

    @Operation(summary = "Get all task")
    @GetMapping(value = "all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.getTasks(id));
    }

    @Operation(summary = "changed task status (PENDING, IN_PROGRESS, COMPLETE)")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<TaskResponse> changeStatus(@RequestBody @Valid ChangeStatusRequest request) {
        return ResponseEntity.ok(taskService.changeStatus(request));
    }

    @Operation(summary = "Delete task by id")
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        taskService.delete(id);

        return ResponseEntity.notFound().build();
    }
}
