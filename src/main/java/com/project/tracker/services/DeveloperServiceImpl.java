package com.project.tracker.services;

import com.project.tracker.dto.requestDto.DeveloperRequestDto;
import com.project.tracker.models.Developer;
import com.project.tracker.services.serviceInterfaces.DeveloperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Override
    public Developer addDeveloper(DeveloperRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteDeveloper(int id) {

    }

    @Override
    public Developer updateDeveloper(DeveloperRequestDto requestDto) {
        return null;
    }

    @Override
    public Developer getDeveloperById(int id) {
        return null;
    }

    @Override
    public List<Developer> getAllDevelopers() {
        return List.of();
    }
}
