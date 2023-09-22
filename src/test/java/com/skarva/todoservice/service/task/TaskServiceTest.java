package com.skarva.todoservice.service.task;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skarva.todoservice.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class TaskServiceTest {

    TaskDBDAO taskDBDAO;
    Gson gsonHelper;
    TaskService taskService;
    String taskString;
    @BeforeEach
    public void init() {
        taskDBDAO = Mockito.mock(TaskDBDAO.class);
        gsonHelper = new GsonBuilder().setPrettyPrinting().create();
        taskService = new TaskService(taskDBDAO, gsonHelper);
        taskString = "{\"taskName\":\"todo\", \"taskStatus\":\"in pending\"}";
    }

    @Test
    public void create_successCase() {
        Mockito.when(taskDBDAO.createTask(any())).thenReturn(gsonHelper.fromJson(taskString, Task.class));
        String actualString = taskService.createTask(taskString);
        Assertions.assertEquals(gsonHelper.fromJson(actualString, Task.class).getTaskName(),
                gsonHelper.fromJson(taskString, Task.class).getTaskName());
    }

    @Test
    public void create_failCase() {
        Mockito.when(taskDBDAO.createTask(any())).thenReturn(null);
        Assertions.assertNull(taskService.createTask(taskString));
    }
}
