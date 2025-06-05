package com.project.tracker.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tracker.dto.requestDto.TaskRequestDto;
import com.project.tracker.dto.responseDto.TaskResponseDto;
import com.project.tracker.exceptions.customExceptions.DeveloperNotFoundException;
import com.project.tracker.exceptions.customExceptions.ProjectNotFoundException;
import com.project.tracker.exceptions.customExceptions.TaskNotFoundException;
import com.project.tracker.models.Developer;
import com.project.tracker.models.Project;
import com.project.tracker.models.Task;
import com.project.tracker.repositories.DeveloperRepository;
import com.project.tracker.repositories.ProjectRepository;
import com.project.tracker.repositories.TaskRepository;
import com.project.tracker.services.serviceInterfaces.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final ObjectMapper objectMapper;
    private final TaskRepository taskRepository;
    private final DeveloperRepository developerRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           ObjectMapper objectMapper,
                           DeveloperRepository developerRepository,
                           ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
        this.developerRepository = developerRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskResponseDto addTask(TaskRequestDto requestDto) {
        //fetch the developer to be assigned to the task
        Developer developer = developerRepository
                .findById(requestDto.developerId())
                .orElseThrow(()-> new DeveloperNotFoundException
                        ("Developer with ID: "+ requestDto.developerId() + "not found"));

        //Fetch the project to which the task is assigned to
        Project project = projectRepository
                .findById(requestDto.projectId())
                .orElseThrow(()-> new ProjectNotFoundException
                        ("Project with ID: "+requestDto.projectId()+" not found"));

        Task task = Task.builder()
                .title(requestDto.title())
                .description(requestDto.description())
                .status(requestDto.status())
                .dueDate(requestDto.dueDate())
                .developer(developer)
                .project(project)
                .build();

        return objectMapper
                .convertValue(taskRepository.save(task),
                        TaskResponseDto.class);
    }

    @Override
    public void deleteTask(int id) {
        //Check if the task exists
        if(!taskRepository.existsById(id)){
           throw new TaskNotFoundException("Task with ID: "+id+" not found");
        }

        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDto updateTask(int id,TaskRequestDto requestDto) {
        //check if the task to be updated exists
        if(!taskRepository.existsById(id)){
            throw new TaskNotFoundException("Task with ID: "+id+" not found");
        }

        //Fetch the project to which the task is assigned to
        Project project = projectRepository
                .findById(requestDto.projectId())
                .orElseThrow(()-> new ProjectNotFoundException
                        ("Project with ID: "+requestDto.projectId()+" not found"));

        //check if the task project is being updated
        if(project.getId() != requestDto.projectId()){
            project = projectRepository
                    .findById(requestDto.projectId())
                    .orElseThrow(()-> new ProjectNotFoundException
                            ("The project you are trying to update to does not exist"));
        }

        Task updatedTask = Task.builder()
                .id(id)
                .title(requestDto.title())
                .description(requestDto.description())
                .project(project)
                .build();
        return objectMapper.
                convertValue(taskRepository.save(updatedTask),
                        TaskResponseDto.class);
    }

    @Override
    public TaskResponseDto getTaskById(int id) {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(()-> new TaskNotFoundException
                        ("Task with ID: "+id+" not found"));
        return objectMapper.convertValue(task, TaskResponseDto.class);
    }

    @Override
    public List<TaskResponseDto> getAllTasks(int pageNumber,String sortBy) {
        int paginateBy = 10;
        //sort
        Sort sort = Sort.by(sortBy);
        //paginator object
        Pageable pageable = PageRequest.of(pageNumber, paginateBy,sort);
        //fetch paginated data
        Page<Task> tasks = taskRepository.findAll(pageable);
        return tasks.stream()
                .map(task -> objectMapper.convertValue(task,TaskResponseDto.class))
                .collect(Collectors.toList());
    }
}
