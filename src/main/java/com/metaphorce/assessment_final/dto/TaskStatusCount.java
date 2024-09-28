package com.metaphorce.assessment_final.dto;

import com.metaphorce.assessment_final.enums.Status;

public interface TaskStatusCount {

    Status getStatus();

    int getCount();

    default boolean isPending() {
        return getStatus() == Status.PENDING;
    }

    default boolean isInProgress() {
        return getStatus() == Status.IN_PROGRESS;
    }

    default boolean isCompleted() {
        return getStatus() == Status.COMPLETE;
    }
}
