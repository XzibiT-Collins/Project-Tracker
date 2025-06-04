package com.project.tracker.services;

import com.project.tracker.dto.requestDto.ProjectRequestDto;
import com.project.tracker.models.Project;
import com.project.tracker.services.serviceInterfaces.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public Project addProject(ProjectRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteProject(int id) {

    }

    @Override
    public Project updateProject(ProjectRequestDto requestDto) {
        return null;
    }

    @Override
    public Project getProjectById(int id) {
        return null;
    }

    @Override
    public List<Project> getAllProjects() {
        return List.of();
    }
}
