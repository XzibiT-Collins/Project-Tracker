package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Task;

import java.util.List;

public record DeveloperResponseDto(
    int id,
    String name,
    String email,
    String skills,
    List<Task>tasks
) {}
