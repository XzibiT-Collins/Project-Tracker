package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Developer;
import com.project.tracker.models.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskResponseDto {
    private int id;
    private String title;
    private String description;
    private String status;
    private String dueDate;
    private Developer developer;
    private Project project;
}
