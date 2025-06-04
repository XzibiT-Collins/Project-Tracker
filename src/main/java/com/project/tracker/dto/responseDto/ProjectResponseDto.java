package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Task;

import java.sql.Date;
import java.util.List;

public record ProjectResponseDto(
    int id,
    String projectName,
    String description,
    Date deadline,
    String status,
    List<Task>tasks
) {}
