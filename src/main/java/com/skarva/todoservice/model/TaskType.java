package com.skarva.todoservice.model;

public enum TaskType {
    COMPLETE("COMPLETE"),
    INCOMPLETE("INCOMPLETE");

    public final String type;

    TaskType(String type) {
        this.type = type;
    }
}
