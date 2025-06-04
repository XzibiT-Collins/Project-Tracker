package com.project.tracker.services;

import com.project.tracker.dto.requestDto.DeveloperRequestDto;
import com.project.tracker.dto.requestDto.TaskRequestDto;
import com.project.tracker.models.Task;
import com.project.tracker.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //save a task
    public Task save(TaskRequestDto taskRequestDto){
        return taskRepository.save();
    }
}
