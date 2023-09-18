package com.skarva.todoservice.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Task {
    private Long taskId;
    private String taskName;
    private String taskStatus;
    private String type;
    private Timestamp created;
    private Timestamp updated;

    public Task(Builder builder) {
        this.taskId = builder.taskId;
        this.taskName = builder.taskName;
        this.taskStatus = builder.taskStatus;
        this.created = builder.created;
        this.updated = builder.updated;
        this.type = builder.type;
    }

    public static class Builder {
        private Long taskId;
        private String taskName;
        private String taskStatus;
        private Timestamp created;
        private Timestamp updated;
        private String type;

        public Builder setTaskId(Long taskId) {
            this.taskId = taskId;
            return this;
        }
        public Builder setTaskName(String taskName) {
            this.taskName = taskName;
            return this;
        }
        public Builder setTaskStatus(String taskStatus) {
            this.taskStatus = taskStatus;
            return this;
        }
        public Builder setCreated(Timestamp created) {
            this.created = created;
            return this;
        }
        public Builder setUpdated(Timestamp updated) {
            this.updated = updated;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }
        public Task build() {
            return new Task(this);
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", type='" + type + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}