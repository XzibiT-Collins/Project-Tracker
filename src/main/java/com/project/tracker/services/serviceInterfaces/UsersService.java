package com.project.tracker.services.serviceInterfaces;

import com.project.tracker.dto.requestDto.UserLoginRequestDto;
import com.project.tracker.dto.requestDto.UsersRequestDto;
import com.project.tracker.dto.responseDto.UserLoginResponseDto;
import com.project.tracker.dto.responseDto.UsersResponseDto;
import org.springframework.data.domain.Page;

public interface UsersService {
    UsersResponseDto addDeveloper(UsersRequestDto requestDto);
    void deleteDeveloper(int id);
    UsersResponseDto updateDeveloper(int id, UsersRequestDto requestDto);
    UsersResponseDto getDeveloperById(int id);
    Page<UsersResponseDto> getAllDevelopers(int pageNumber, String sortBy);
    Page<UsersResponseDto> getTopDevelopers();
    UsersResponseDto registerUser(UsersRequestDto requestDto);
    UserLoginResponseDto loginUser(UserLoginRequestDto requestDto);
}
