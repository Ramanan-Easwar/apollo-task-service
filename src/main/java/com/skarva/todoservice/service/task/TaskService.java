package com.skarva.todoservice.service.task;

import com.google.gson.Gson;
import com.skarva.todoservice.common.QueryHelper;
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
    Gson gsonHelper;

    @Autowired
    public TaskService(TaskDBDAO taskDBDAO, Gson gsonHelper) {
        this.taskDBDAO = taskDBDAO;
        this.gsonHelper = gsonHelper;
    }

    public String createTask(String taskJson) {
        LOG.error("obj from {}", taskJson);
        Task task = gsonHelper.fromJson(taskJson, Task.class);
        task.setCreated(new Timestamp(System.currentTimeMillis()));
        task.setType(TaskType.INCOMPLETE.type);
        task.setTaskUUID(QueryHelper.generateUUID(task.getTaskName() + task.getTaskId()));
        task = taskDBDAO.createTask(task);
        if(task == null) {
            LOG.error("Could not create the task!!");
            return null;
        }
        return gsonHelper.toJson(task);
    }


    public String createTaskByUser(String taskJson, String userAlias) {

        Task task = gsonHelper.fromJson(taskJson, Task.class);
        task.setUserAlias(userAlias);
        task.setCreated(new Timestamp(System.currentTimeMillis()));
        task.setType(TaskType.INCOMPLETE.type);
        task.setTaskUUID(QueryHelper.generateUUID(task.getTaskName() + task.getTaskId()));
        task = taskDBDAO.createTask(task);
        if(task == null) {
            LOG.error("unable to create task!!");
            return null;
        }
        return gsonHelper.toJson(task);
    }

    public String completeStatus(String uuid) {
        LOG.info("obj from {}", uuid);
        String taskName = taskDBDAO.completeTask(uuid);
        if(taskName == null) {
            LOG.error("Could not create the task!!");
            return null;
        }
        return taskName;
    }


    public List<Task> getAllTasksByUser(String userUuid) {
        return taskDBDAO.returnAllTasksByUserAlias(userUuid);
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

    public String getTask(String uuid) {
        Task task = taskDBDAO.returnTask(uuid);
        if(task == null) {
            LOG.error("Could not fetch the task!! {}", uuid);
            return null;
        }

        return gsonHelper.toJson(task);
    }

}
