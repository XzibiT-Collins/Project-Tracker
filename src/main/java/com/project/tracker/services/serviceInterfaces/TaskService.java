package com.project.tracker.services.serviceInterfaces;

import com.project.tracker.dto.requestDto.TaskRequestDto;
import com.project.tracker.dto.responseDto.TaskResponseDto;
import com.project.tracker.models.Task;

import java.util.List;

public interface TaskService {
    TaskResponseDto addTask(TaskRequestDto requestDto);
    void deleteTask(int id);
    TaskResponseDto updateTask(int id,TaskRequestDto requestDto);
    TaskResponseDto getTaskById(int id);
    List<TaskResponseDto> getAllTasks(int pageNumber,String sortBy);
}
