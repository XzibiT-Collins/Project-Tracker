package com.project.tracker.services.serviceInterfaces;

import com.project.tracker.dto.requestDto.ProjectRequestDto;
import com.project.tracker.models.Project;

import java.util.List;

public interface ProjectService {
    Project addProject(ProjectRequestDto requestDto);
    void deleteProject(int id);
    Project updateProject(ProjectRequestDto requestDto);
    Project getProjectById(int id);
    List<Project> getAllProjects();
}
