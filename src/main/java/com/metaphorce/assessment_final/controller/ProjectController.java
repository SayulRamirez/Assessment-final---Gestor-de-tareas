package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.dto.*;
import com.metaphorce.assessment_final.services.ProjectService;
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

@CrossOrigin(origins = {"http://localhost:5173", "http://192.168.100.244:5173/", "http://127.0.0.1:5500/"})
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "create new project colaboratory", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<ProjectResponse> create(@RequestBody @Valid ProjectRequest request) {

        ProjectResponse project = projectService.createProject(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(project.id()).toUri();

        return ResponseEntity.created(location).body(project);
    }

    @Operation(summary = "Get a project", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {

        return ResponseEntity.ok(projectService.getProject(id));
    }

    @Operation(summary = "Get all project", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProjectResponse>> getAllProjects(@PathVariable Long id) {

        return ResponseEntity.ok(projectService.getProjects(id));
    }

    @Operation(summary = "Change status project (PENDING, IN_PROGRESS, COMPLETE", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<ProjectResponse> changeStatus(@RequestBody @Valid ChangeStatusRequest request) {

        return ResponseEntity.ok(projectService.changeStatus(request));
    }

    @Operation(summary = "delete project by id", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get report project progress", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "report/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Report>> getReport(@PathVariable Long id) {

        return ResponseEntity.ok(projectService.getReport(id));
    }
}
