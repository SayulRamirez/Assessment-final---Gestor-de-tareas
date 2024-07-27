package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.UpdateInfoRequest;
import com.metaphorce.assessment_final.dto.UserResponse;
import com.metaphorce.assessment_final.entities.User;
import com.metaphorce.assessment_final.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getInfo(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found whit id: " + id));

        return new UserResponse(user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getMaternalSurname(),
                    user.getPhoneNumber(),
                    user.getStatus(),
                    user.getEmail());
    }

    @Override
    public UserResponse updateInfo(UpdateInfoRequest request) {

        User user = userRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("User not found whit id: " + request.id()));

        user.setFirstName(request.first_name());
        user.setLastName(request.last_name());
        user.setPhoneNumber(request.phone_number());

        if (request.maternal_surname().isBlank()) user.setMaternalSurname(request.maternal_surname());

        userRepository.save(user);

        return new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMaternalSurname(),
                user.getPhoneNumber(),
                user.getStatus(),
                user.getEmail());
    }

    @Override
    public UserResponse changeStatus(ChangeStatusRequest request) {

        User user = userRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("User not found whit id: " + request.id()));

        user.setStatus(request.status());

        userRepository.save(user);

        return new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMaternalSurname(),
                user.getPhoneNumber(),
                user.getStatus(),
                user.getEmail());
    }
}
