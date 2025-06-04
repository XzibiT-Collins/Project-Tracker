package com.project.tracker.services.serviceInterfaces;

import com.project.tracker.dto.requestDto.DeveloperRequestDto;
import com.project.tracker.models.Developer;

import java.util.List;

public interface DeveloperService {
    Developer addDeveloper(DeveloperRequestDto requestDto);
    void deleteDeveloper(int id);
    Developer updateDeveloper(DeveloperRequestDto requestDto);
    Developer getDeveloperById(int id);
    List<Developer> getAllDevelopers();
}
