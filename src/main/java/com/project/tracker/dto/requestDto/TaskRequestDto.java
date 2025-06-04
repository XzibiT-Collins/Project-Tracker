package com.project.tracker.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class TaskRequestDto {
    @NotBlank(message = "Task must have a title.")
    private String title;

    @Size(max = 500, message = "Description cannot be more than 500 characters.")
    private String description;

    @NotBlank(message = "Task must have a status.")
    private String status;

    @NotNull(message = "Task must have a due date.")
    private Date dueDate;

    private int developerId;

    @NotNull(message = "Task must belong to a project.")
    private int projectId;
}
