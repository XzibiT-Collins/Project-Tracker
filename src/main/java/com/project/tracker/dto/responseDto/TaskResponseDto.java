package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Developer;
import com.project.tracker.models.Project;

public record TaskResponseDto(
        int id,
        String title,
        String description,
        String status,
        String dueDate,
        Developer developer,
        Project project
) {}
