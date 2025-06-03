package com.project.tracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Developer {
    @Id
    private int id;
    private String name;
    private String email;
    private List<String> skills;
}
