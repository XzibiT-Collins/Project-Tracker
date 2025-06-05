package com.project.tracker.controllers;

import com.project.tracker.dto.requestDto.TaskRequestDto;
import com.project.tracker.dto.responseDto.TaskResponseDto;
import com.project.tracker.services.serviceInterfaces.TaskService;
import com.project.tracker.sortingEnums.TaskSorting;
import jakarta.validation.Valid;
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

    @PostMapping("/create")
    protected ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.addTask(request));
    }

    @DeleteMapping("delete/{id}")
    protected ResponseEntity<String> deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Task Deleted Successfully");
    }

    @PutMapping("update/{id}")
    protected ResponseEntity<TaskResponseDto> updateTask(@PathVariable int id, @Valid @RequestBody TaskRequestDto request ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.updateTask(id,request));
    }

    @GetMapping("/{id}")
    protected ResponseEntity<TaskResponseDto> getTask(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @GetMapping
    protected ResponseEntity<Page<TaskResponseDto>> getAllTasks(
            @RequestParam(required = false, defaultValue = "SORT_BY_TITLE") TaskSorting sortBy,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getAllTasks(pageNumber,sortBy.getField()));
    }

    @GetMapping("/overdueTasks")
    protected ResponseEntity<Page<TaskResponseDto>> getAllOverdueTasks(
            @RequestParam(required = false, defaultValue = "SORT_BY_TITLE") TaskSorting sortBy,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getOverdueTasks(pageNumber,sortBy.getField()));
    }
}
