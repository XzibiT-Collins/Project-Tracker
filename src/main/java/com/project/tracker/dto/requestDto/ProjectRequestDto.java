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
public class ProjectRequestDto {
    @NotBlank(message = "Project name must not be blank.")
    private String projectName;

    @Size(max = 500, message = "Description cannot be more than 500 characters")
    private String description;

    @NotNull(message = "Project must have a deadline.")
    private Date deadline;

    @NotBlank(message = "Project must have a status.")
    private String status;
}
