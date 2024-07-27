package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.ProjectRequest;
import com.metaphorce.assessment_final.dto.ProjectResponse;
import com.metaphorce.assessment_final.services.ProjectService;
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
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "create new project colaboratory")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<ProjectResponse> create(@RequestBody @Valid ProjectRequest request) {

        ProjectResponse project = projectService.createProject(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(project.id()).toUri();

        return ResponseEntity.created(location).body(project);
    }

    @Operation(summary = "Get a project")
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {

        return ResponseEntity.ok(projectService.getProject(id));
    }

    @Operation(summary = "Get all project")
    @GetMapping(value = "all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProjectResponse>> getAllProjects(@PathVariable Long id) {

        return ResponseEntity.ok(projectService.getProjects(id));
    }

    @Operation(summary = "Change status project (PENDING, IN_PROGRESS, COMPETE")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<ProjectResponse> changeStatus(@RequestBody @Valid ChangeStatusRequest request) {

        return ResponseEntity.ok(projectService.changeStatus(request));
    }
}
