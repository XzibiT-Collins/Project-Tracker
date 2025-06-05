package com.project.tracker.dto.requestDto;

import com.project.tracker.models.Task;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

public record DeveloperRequestDto(
    @NotBlank(message = "Name cannot be blank.")
    String name,

    @NotBlank(message = "Please provide an email.")
    @Email(message = "Please provide a valid email.")
    String email,

    @NotEmpty(message = "Please add a skill.")
    Set<String> skills
) {}
