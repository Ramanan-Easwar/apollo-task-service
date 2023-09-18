package com.skarva.todoservice.service.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skarva.todoservice.model.Task;
import com.skarva.todoservice.model.TaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class TaskService {

    Logger LOG = LoggerFactory.getLogger(TaskService.class);
    TaskDBDAO taskDBDAO;

    @Autowired
    public TaskService(TaskDBDAO taskDBDAO) {
        this.taskDBDAO = taskDBDAO;
    }

    public String createTask(String taskJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        LOG.error("obj from {}", taskJson);
        Task task = gson.fromJson(taskJson, Task.class);
        task.setCreated(new Timestamp(System.currentTimeMillis()));
        task.setType(TaskType.INCOMPLETE.type);
        task = taskDBDAO.createTask(task);
        if(task == null) {
            LOG.error("Could not create the task!!");
            return null;
        }
        return gson.toJson(task);
    }

    public String completeStatus(long id) {
        LOG.info("obj from {}", id);
        String taskName = taskDBDAO.completeTask(id);
        if(taskName == null) {
            LOG.error("Could not create the task!!");
            return null;
        }
        return taskName;
    }


    public List<Task> getAllTasks() {
        return taskDBDAO.returnAllTasks();
    }

    public List<Task> getCompletedTasks() {
        return taskDBDAO.returnAllTasksWithCondition(TaskType.COMPLETE.type);
    }

    public List<Task> getIncompleteTasks() {
        return taskDBDAO.returnAllTasksWithCondition(TaskType.INCOMPLETE.type);
    }

    public String getTask(Long id) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        LOG.error("obj from {}", id);
        Task task = taskDBDAO.returnTask(id);
        if(task == null) {
            LOG.error("Could not fetch the task!! {}", id);
            return null;
        }
        return gson.toJson(task);
    }
}
