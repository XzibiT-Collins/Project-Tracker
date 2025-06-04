package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Task;

import java.util.List;
import java.util.Set;

public record DeveloperResponseDto(
    int id,
    String name,
    String email,
    Set<String> skills,
    List<Task>tasks
) {}
