package com.metaphorce.assessment_final.services;

import com.metaphorce.assessment_final.dto.ChangeStatusRequest;
import com.metaphorce.assessment_final.dto.TaskRequest;
import com.metaphorce.assessment_final.dto.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    TaskResponse getTask(Long id);

    List<TaskResponse> getTasks(Long id);

    TaskResponse changeStatus(ChangeStatusRequest request);

    void delete(Long id);
}
