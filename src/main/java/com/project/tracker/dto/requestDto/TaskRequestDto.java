package com.project.tracker.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public record TaskRequestDto(
        @NotBlank(message = "Task must have a title.")
        String title,

        @Size(max = 500, message = "Description cannot be more than 500 characters.")
        String description,

        @NotBlank(message = "Task must have a status.")
        String status,

        @NotNull(message = "Task must have a due date.")
        Date dueDate,

        int developerId,

        @NotNull(message = "Task must belong to a project.")
        int projectId
) {}
