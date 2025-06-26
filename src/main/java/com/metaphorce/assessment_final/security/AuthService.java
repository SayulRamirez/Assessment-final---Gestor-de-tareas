package com.metaphorce.assessment_final.security;

import com.metaphorce.assessment_final.dto.AuthResponse;
import com.metaphorce.assessment_final.dto.LoginRequest;
import com.metaphorce.assessment_final.dto.RegisterRequest;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.Role;
import com.metaphorce.assessment_final.enums.UserStatus;
import com.metaphorce.assessment_final.exceptions.EntityNotActiveException;
import com.metaphorce.assessment_final.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getStatus().getStatus()) throw new EntityNotActiveException("The user is blocked or delete");

        String token = jwtService.getToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) throw new EntityExistsException("Email exists: " + request.email());

        if (userRepository.existsByPhoneNumber(request.phone_number())) throw new EntityExistsException("Phone exists: " + request.phone_number());

        User user = userRepository.save(User.builder()
                .firstName(request.first_name())
                .lastName(request.last_name())
                .maternalSurname(request.maternal_surname())
                .phoneNumber(request.phone_number())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .status(UserStatus.ACTIVE)
                .role(Role.USER).build());

        return new AuthResponse(jwtService.getToken(user));
    }
}
