package com.project.tracker.dto.responseDto;

public record UserLoginResponseDto(
        String name,
        String email,
        String token
){}
