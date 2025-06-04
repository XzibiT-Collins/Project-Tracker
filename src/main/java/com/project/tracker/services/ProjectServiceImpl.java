package com.project.tracker.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tracker.dto.requestDto.ProjectRequestDto;
import com.project.tracker.dto.responseDto.ProjectResponseDto;
import com.project.tracker.models.Project;
import com.project.tracker.repositories.ProjectRepository;
import com.project.tracker.services.serviceInterfaces.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ObjectMapper objectMapper;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ObjectMapper objectMapper) {
        this.projectRepository = projectRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ProjectResponseDto addProject(ProjectRequestDto requestDto) {
        Project project = Project.builder()
                .projectName(requestDto.projectName())
                .description(requestDto.description())
                .deadline(requestDto.deadline())
                .status(requestDto.status())
                .build();
        return objectMapper.convertValue(projectRepository.save(project), ProjectResponseDto.class);
    }

    @Override
    public void deleteProject(int id) {
        Project project = projectRepository.findById(id).orElseThrow();//Throw exception if not found
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectResponseDto updateProject(int id,ProjectRequestDto requestDto) {
        //fetch project to be updated
        if(!projectRepository.existsById(id)){
            //Throw exception if not found
        }

        Project updatedProject = Project.builder()
                .id(id)
                .projectName(requestDto.projectName())
                .description(requestDto.description())
                .deadline(requestDto.deadline())
                .status(requestDto.status())
                .build();
        return objectMapper.convertValue(projectRepository.save(updatedProject), ProjectResponseDto.class);
    }

    @Override
    public ProjectResponseDto getProjectById(int id) {
        Project project = projectRepository.findById(id).orElseThrow(); // Throw exception if not found
        return objectMapper.convertValue(project, ProjectResponseDto.class);
    }

    @Override
    public List<ProjectResponseDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> objectMapper.
                        convertValue(project, ProjectResponseDto.class))
                .collect(Collectors.toList());
    }
}
