package com.project.tracker.controllers;

import com.project.tracker.dto.requestDto.TaskRequestDto;
import com.project.tracker.dto.responseDto.TaskResponseDto;
import com.project.tracker.services.serviceInterfaces.TaskService;
import com.project.tracker.sortingEnums.TaskSorting;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @CacheEvict(value = {"tasks","overdueTasks"}, allEntries = true)
    @PostMapping("/create")
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.addTask(request));
    }

    @CacheEvict(value = {"tasks","overdueTasks"}, allEntries = true)
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Task Deleted Successfully");
    }

    @CacheEvict(value = {"tasks","overdueTasks"}, allEntries = true)
    @PutMapping("update/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable int id, @Valid @RequestBody TaskRequestDto request ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.updateTask(id,request));
    }

    @Cacheable(value = "tasks", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @Cacheable(value = "tasks", key = "T(java.util.Objects).hash(#pageNumber, #sortBy)")
    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getAllTasks(
            @RequestParam(required = false, defaultValue = "SORT_BY_TITLE") TaskSorting sortBy,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getAllTasks(pageNumber,sortBy.getField()));
    }

    @Cacheable(value = "overdueTasks", key = "T(java.util.Objects).hash(#pageNumber, #sortBy)")
    @GetMapping("/overdueTasks")
    public ResponseEntity<Page<TaskResponseDto>> getAllOverdueTasks(
            @RequestParam(required = false, defaultValue = "SORT_BY_TITLE") TaskSorting sortBy,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getOverdueTasks(pageNumber,sortBy.getField()));
    }
}
