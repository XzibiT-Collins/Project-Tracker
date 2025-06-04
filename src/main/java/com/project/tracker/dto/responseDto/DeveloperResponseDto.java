package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DeveloperResponseDto {
    private int id;
    private String name;
    private String email;
    private String skills;
    private List<Task> tasks;
}
