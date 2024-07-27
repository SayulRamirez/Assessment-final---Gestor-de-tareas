package com.metaphorce.assessment_final.controller;

import com.metaphorce.assessment_final.exceptions.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, Object> response = new LinkedHashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            response.put(field, message);
        });

        response.put("timestamp", ZonedDateTime.now(ZoneId.of("America/Chicago")));
        response.put("status", HttpStatus.BAD_REQUEST);

        return response;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException e) {

        return new ExceptionResponse(
                e.getMessage(),
                ZonedDateTime.now(ZoneId.systemDefault()),
                HttpStatus.NOT_FOUND
        );
    }
}
