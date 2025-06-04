package com.project.tracker.services.serviceInterfaces;

import com.project.tracker.dto.requestDto.TaskRequestDto;
import com.project.tracker.models.Task;

import java.util.List;

public interface TaskService {
    Task addTask(TaskRequestDto requestDto);
    void deleteTask(int id);
    Task updateTask(TaskRequestDto requestDto);
    Task getTaskById(int id);
    List<Task> getAllTasks();
}
