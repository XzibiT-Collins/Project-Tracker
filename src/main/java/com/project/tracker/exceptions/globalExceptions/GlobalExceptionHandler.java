package com.project.tracker.exceptions.globalExceptions;

import com.project.tracker.exceptions.customExceptions.DeveloperNotFoundException;
import com.project.tracker.exceptions.customExceptions.ProjectNotFoundException;
import com.project.tracker.exceptions.customExceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler{
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status){

        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .build()
        );
    }


    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFound(ProjectNotFoundException exception) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return buildErrorResponse(exception.getMessage(), notFound);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException exception){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return buildErrorResponse(exception.getMessage(), notFound);
    }

    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeveloperNotFound(DeveloperNotFoundException exception){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return buildErrorResponse(exception.getMessage(), notFound);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        String errorMessage = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField()+ " : "+ error.getDefaultMessage())
                .collect(Collectors.joining("\n"));

        return buildErrorResponse(errorMessage, badRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception exception){
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return buildErrorResponse(exception.getMessage(), internalServerError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException exception){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return buildErrorResponse(exception.getMessage(), badRequest);
    }
}
