package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Task;
import com.project.tracker.sortingEnums.StatusEnum;

import java.sql.Date;
import java.util.List;

public record ProjectResponseDto(
    int id,
    String projectName,
    String description,
    Date deadline,
    StatusEnum status,
    List<Task>tasks
) {}
