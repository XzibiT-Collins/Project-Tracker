package com.project.tracker.exceptions.customExceptions;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(String message) {
        super(message);
    }
}
