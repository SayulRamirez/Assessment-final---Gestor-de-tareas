package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.dto.AuthResponse;
import com.metaphorce.assessment_final.dto.LoginRequest;
import com.metaphorce.assessment_final.dto.RegisterRequest;
import com.metaphorce.assessment_final.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://192.168.100.244:5173/",
        "http://192.168.100.170:80",
        "http://192.168.100.170:5173",
        "http://127.0.0.1:5500/"
})
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
