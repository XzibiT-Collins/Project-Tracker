package com.project.tracker.dto.responseDto;

import com.project.tracker.statusEnum.StatusEnum;

public record TaskResponseDto(
        int id,
        String title,
        String description,
        StatusEnum status,
        String dueDate,
        String developer,
        String project
) {}
