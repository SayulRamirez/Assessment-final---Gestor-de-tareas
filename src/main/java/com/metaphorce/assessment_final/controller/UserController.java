package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.dto.StatusUserRequest;
import com.metaphorce.assessment_final.dto.UpdateInfoRequest;
import com.metaphorce.assessment_final.dto.UserResponse;
import com.metaphorce.assessment_final.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get information user")
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getInfo(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getInfo(id));
    }

    @Operation(summary = "Change info user")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<UserResponse> updateInfo(@RequestBody @Valid UpdateInfoRequest request) {

        return ResponseEntity.ok(userService.updateInfo(request));
    }

    @Operation(summary = "Change status user")
    @PutMapping(value = "status")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<UserResponse> changeStatus(@RequestBody @Valid StatusUserRequest request) {
        return ResponseEntity.ok(userService.changeStatus(request));
    }
}
