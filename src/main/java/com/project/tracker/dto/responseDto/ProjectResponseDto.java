package com.project.tracker.dto.responseDto;

import com.project.tracker.models.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ProjectResponseDto {
    private int id;
    private String projectName;
    private String description;
    private Date deadline;
    private String status;
    private List<Task> tasks;
}
