package com.project.tracker.services.serviceInterfaces;

import com.project.tracker.dto.requestDto.DeveloperRequestDto;
import com.project.tracker.dto.responseDto.DeveloperResponseDto;

import java.util.List;

public interface DeveloperService {
    DeveloperResponseDto addDeveloper(DeveloperRequestDto requestDto);
    void deleteDeveloper(int id);
    DeveloperResponseDto updateDeveloper(int id,DeveloperRequestDto requestDto);
    DeveloperResponseDto getDeveloperById(int id);
    List<DeveloperResponseDto> getAllDevelopers(int pageNumber,String sortBy);
}
