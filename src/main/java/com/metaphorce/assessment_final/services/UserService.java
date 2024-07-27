package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.UpdateInfoRequest;
import com.metaphorce.assessment_final.dto.UserResponse;

public interface UserService {

    UserResponse getInfo(Long id);

    UserResponse updateInfo(UpdateInfoRequest request);

    UserResponse changeStatus(ChangeStatusRequest request);
}
