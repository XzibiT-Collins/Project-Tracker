package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Developer;
import com.project.tracker.models.Project;
import com.project.tracker.statusEnum.StatusEnum;

public record TaskResponseDto(
        int id,
        String title,
        String description,
        StatusEnum status,
        String dueDate,
        Developer developer,
        Project project
) {}
