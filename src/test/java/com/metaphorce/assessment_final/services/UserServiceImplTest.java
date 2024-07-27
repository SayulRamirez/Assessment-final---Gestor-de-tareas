package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.StatusUserRequest;
import com.metaphorce.assessment_final.dto.UpdateInfoRequest;
import com.metaphorce.assessment_final.dto.UserResponse;
import com.metaphorce.assessment_final.repositories.UserRepository;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.enums.UserStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl underTest;

    //getInfo()
    @Test
    void whenGetInfoUserIsNotFound() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.getInfo(1L));
    }

    @Test
    void whenGetInfoIsSuccessful() {

        User user = User.builder()
                .id(1L)
                .firstName("juan")
                .lastName("ramirez")
                .maternalSurname("vazquez")
                .phoneNumber("48822412432")
                .status(UserStatus.ACTIVE)
                .email("juan@example.com")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserResponse response = underTest.getInfo(1L);

        assertEquals(user.getId(), response.id());
        assertEquals(user.getEmail(), response.email());
    }

    //updateInfo()
    @Test
    void whenUpdateInfoUserIsNotFound() {

        UpdateInfoRequest request = new UpdateInfoRequest(
                1L,
                "juan",
                "perez",
                "peña",
                "3212347327"
        );

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.updateInfo(request));
    }

    @Test
    void whenUpdateInfoIsSuccessful() {

        User user = User.builder()
                .id(1L)
                .firstName("juan")
                .lastName("ramirez")
                .maternalSurname("vazquez")
                .phoneNumber("48822412432")
                .status(UserStatus.ACTIVE)
                .email("juan@example.com")
                .build();

        UpdateInfoRequest request = new UpdateInfoRequest(
                1L,
                "juan",
                "perez",
                "peña",
                "3212347327"
        );

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserResponse result = underTest.updateInfo(request);

        assertEquals(request.id(), result.id());
        assertEquals(request.last_name(), result.last_name());
        assertEquals(request.maternal_surname(), result.maternal_surname());
        assertEquals(request.phone_number(), result.phone_number());
    }

    //changedStatus()
    @Test
    void whenChangeStatusUserNotFound() {

        StatusUserRequest request = new StatusUserRequest(1L, UserStatus.BLOCKED);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.changeStatus(request));
    }

    @Test
    void whenChangeStatusIsSuccessful() {

        User user = User.builder()
                .id(1L)
                .firstName("juan")
                .lastName("ramirez")
                .maternalSurname("vazquez")
                .phoneNumber("48822412432")
                .status(UserStatus.ACTIVE)
                .email("juan@example.com")
                .build();

        StatusUserRequest request = new StatusUserRequest(1L, UserStatus.BLOCKED);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserResponse result = underTest.changeStatus(request);

        assertEquals(request.status(), result.status());
    }
}