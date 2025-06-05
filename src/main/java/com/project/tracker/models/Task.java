package com.project.tracker.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.tracker.statusEnum.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Date dueDate;

    @ManyToOne(optional = true) //optional assignment to a Developer
    @JoinColumn(name = "developer_id")
    @JsonBackReference(value = "developer-task")
    private Developer developer;

    @ManyToOne(optional = true)
    @JoinColumn(name = "project_id")
    @JsonBackReference(value = "project-task")
    private Project project;
}