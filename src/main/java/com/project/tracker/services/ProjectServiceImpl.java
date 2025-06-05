package com.project.tracker.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tracker.dto.requestDto.ProjectRequestDto;
import com.project.tracker.dto.responseDto.ProjectResponseDto;
import com.project.tracker.exceptions.customExceptions.ProjectNotFoundException;
import com.project.tracker.models.Project;
import com.project.tracker.repositories.ProjectRepository;
import com.project.tracker.services.serviceInterfaces.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    @Transactional
    public void deleteProject(int id) {
        Project project = projectRepository
                .findById(id)
                .orElseThrow(()-> new ProjectNotFoundException
                        ("Project with ID: "+ id +" not found"));
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectResponseDto updateProject(int id,ProjectRequestDto requestDto) {
        //fetch project to be updated
        if(!projectRepository.existsById(id)){
            throw new ProjectNotFoundException("Project with ID: "+id+" not found");
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
        Project project = projectRepository
                .findById(id)
                .orElseThrow(()-> new ProjectNotFoundException
                        ("Project with ID: "+ id +" not found"));
        return objectMapper.convertValue(project, ProjectResponseDto.class);
    }

    @Override
    public Page<ProjectResponseDto> getAllProjects(int pageNumber, String sortBy) {
        //paginate by
        int paginateBy = 10;

        //Sorting param
        Sort sort = Sort.by(sortBy);

        //Pageable object
        Pageable pageable = PageRequest.of(pageNumber, paginateBy, sort);

        Page<Project> projects = projectRepository.findAll(pageable);
        projects.forEach(System.out::println);
        return projects
                .map(project -> objectMapper.
                        convertValue(project, ProjectResponseDto.class))
                ;
    }
}
