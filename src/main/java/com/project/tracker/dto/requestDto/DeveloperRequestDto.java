package com.project.tracker.dto.requestDto;

import com.project.tracker.models.Task;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeveloperRequestDto {
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Please provide an email.")
    @Email(message = "Please provide a valid email.")
    private String email;

    @NotBlank(message = "Please add a skill.")
    private String skills;

    @OneToMany(mappedBy = "developer")
    private List<Task> tasks;
}
