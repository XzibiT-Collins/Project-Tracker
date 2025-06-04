package com.project.tracker.services;

import com.project.tracker.dto.requestDto.TaskRequestDto;
import com.project.tracker.models.Task;
import com.project.tracker.repositories.TaskRepository;
import com.project.tracker.services.serviceInterfaces.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTask(TaskRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteTask(int id) {

    }

    @Override
    public Task updateTask(TaskRequestDto requestDto) {
        return null;
    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return List.of();
    }
}
