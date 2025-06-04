package com.project.tracker.exceptions.globalExceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class ErrorResponse {
    public String message;
    public LocalDateTime timestamp;
    public int status;
}
