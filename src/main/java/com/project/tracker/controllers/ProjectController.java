package com.project.tracker.controllers;

import com.project.tracker.dto.requestDto.ProjectRequestDto;
import com.project.tracker.dto.responseDto.ProjectResponseDto;
import com.project.tracker.services.serviceInterfaces.ProjectService;
import com.project.tracker.sortingEnums.ProjectSorting;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    protected ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.addProject(request));
    }

    @DeleteMapping("delete/{id}")
    protected ResponseEntity<String> deleteProject(@PathVariable int id){
        projectService.deleteProject(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Project Deleted Successfully");
    }

    @PutMapping("update/{id}")
    protected ResponseEntity<ProjectResponseDto> updateProject(@PathVariable int id, @Valid @RequestBody ProjectRequestDto request ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.updateProject(id,request));
    }

    @GetMapping("/{id}")
    protected ResponseEntity<ProjectResponseDto> getProject(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.getProjectById(id));
    }

    @GetMapping
    protected ResponseEntity<Page<ProjectResponseDto>> getAllProjects(
            @RequestParam(required = false, defaultValue = "SORT_BY_ID") ProjectSorting sortBy,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.getAllProjects(pageNumber,sortBy.getField()));
    }
}
