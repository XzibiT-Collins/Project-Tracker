package com.project.tracker.statusEnum;

import lombok.Getter;

@Getter

public enum TaskSorting {
    SORT_BY_DUE_DATE("dueDate"),
    SORT_BY_STATUS("status");

    private final String field;
    TaskSorting(String field){
        this.field = field;
    }
}
