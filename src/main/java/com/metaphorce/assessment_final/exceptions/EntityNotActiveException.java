package com.metaphorce.assessment_final.exceptions;

public class EntityNotActiveException extends RuntimeException {

    public EntityNotActiveException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
