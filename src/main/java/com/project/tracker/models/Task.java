package com.project.tracker.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String status;
    private Date dueDate;

    @ManyToOne(optional = true) //optional assignment to a Developer
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @ManyToOne(optional = true)
    @JoinColumn(name = "project_id")
    private Project project;
}