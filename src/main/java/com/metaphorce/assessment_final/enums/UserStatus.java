package com.metaphorce.assessment_final.enums;

public enum UserStatus {

    ACTIVE(true),

    BLOCKED(false),

    DELETED(false);

    private final boolean status;

    UserStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
