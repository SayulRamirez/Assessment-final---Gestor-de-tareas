package com.metaphorce.assessment_final.security;

import com.metaphorce.assessment_final.dto.AuthResponse;
import com.metaphorce.assessment_final.dto.LoginRequest;
import com.metaphorce.assessment_final.dto.RegisterRequest;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.UserStatus;
import com.metaphorce.assessment_final.exceptions.EntityNotActiveException;
import com.metaphorce.assessment_final.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService underTest;

    @Nested
    class LoginUser {

        private LoginRequest request;

        @BeforeEach
        void setup() {
            request = new LoginRequest("juan@example.com", "juan1234");
        }

        @Test
        void whenLoginUserIsNotAuthenticate() {

            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(BadCredentialsException.class);

            assertThrows(BadCredentialsException.class, () -> underTest.login(request));
        }

        @Test
        void whenUserNotFoundByEmail() {
            when(userRepository.findByEmail(anyString()))
                    .thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> underTest.login(request));
        }

        @Test
        void whenUserIsNotActive() {
            when(userRepository.findByEmail(anyString()))
                    .thenReturn(Optional.of(User.builder().id(1L).status(UserStatus.BLOCKED).email(request.email()).build()));

            assertThrows(EntityNotActiveException.class, () -> underTest.login(request));
        }

        @Test
        void whenLoginIsSuccessful() {

            when(userRepository.findByEmail(anyString()))
                    .thenReturn(Optional.of(User.builder().id(1L).status(UserStatus.ACTIVE).email(request.email()).build()));

            when(jwtService.getToken(any(User.class)))
                    .thenCallRealMethod();

            AuthResponse response = underTest.login(request);

            assertNotNull(response.token());
        }
    }

    @Nested
    class RegisterUser {

        private RegisterRequest request;

        @BeforeEach
        void setup() {
            request = new RegisterRequest("juan",
                    "perez", "meza",
                    "4774321254",
                    "juan@example.com", "juan1234"
            );
        }

        //register
        @Test
        void whenRegisterEmailAlreadyExists() {

            when(userRepository.existsByEmail(anyString()))
                    .thenReturn(true);

            assertThrows(EntityExistsException.class, () -> underTest.register(request));
        }

        @Test
        void whenRegisterPhoneNumberAlreadyExists(){
            when(userRepository.existsByEmail(anyString()))
                    .thenReturn(false);

            when(userRepository.existsByPhoneNumber(anyString()))
                    .thenReturn(true);

            assertThrows(EntityExistsException.class, () -> underTest.register(request));
        }

        @Test
        void whenRegisterIsSuccessful() {

            when(userRepository.existsByEmail(anyString()))
                    .thenReturn(false);

            when(userRepository.existsByPhoneNumber(anyString()))
                    .thenReturn(false);

            when(passwordEncoder.encode(any(CharSequence.class)))
                    .thenReturn("encodePassword");

            when(jwtService.getToken(any(User.class)))
                    .thenCallRealMethod();

            when(userRepository.save(any(User.class)))
                    .thenReturn(User.builder().id(1L).email(request.email()).build());

            AuthResponse response = underTest.register(request);

            assertNotNull(response.token());
            verify(userRepository, times(1)).save(any(User.class));
        }
    }
}
