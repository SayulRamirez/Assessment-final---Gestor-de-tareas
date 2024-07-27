package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.UpdateInfoRequest;
import com.metaphorce.assessment_final.dto.UserResponse;
import com.metaphorce.assessment_final.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public UserResponse getInfo(@PathVariable Long id) {
        return userService.getInfo(id);
    }

    @Operation(summary = "Change info user")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public UserResponse updateInfo(@RequestBody @Valid UpdateInfoRequest request) {
        return userService.updateInfo(request);
    }

    @Operation(summary = "Change status user")
    @PutMapping(value = "status")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public UserResponse changeStatus(@RequestBody @Valid ChangeStatusRequest request) {
        return userService.changeStatus(request);
    }
}
