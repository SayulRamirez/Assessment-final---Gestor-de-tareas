package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.dto.StatusUserRequest;
import com.metaphorce.assessment_final.dto.UpdateInfoRequest;
import com.metaphorce.assessment_final.dto.UserResponse;
import com.metaphorce.assessment_final.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get information user", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getInfo(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getInfo(id));
    }

    @Operation(summary = "Change info user", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<UserResponse> updateInfo(@RequestBody @Valid UpdateInfoRequest request) {

        return ResponseEntity.ok(userService.updateInfo(request));
    }

    @Operation(summary = "Change status user", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "status")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<UserResponse> changeStatus(@RequestBody @Valid StatusUserRequest request) {
        return ResponseEntity.ok(userService.changeStatus(request));
    }
}
