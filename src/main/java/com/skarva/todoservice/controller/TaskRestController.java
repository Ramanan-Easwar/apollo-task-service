package com.skarva.todoservice.controller;


import com.skarva.todoservice.model.Task;
import com.skarva.todoservice.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskRestController {

    TaskService taskService;

    @Autowired
    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String healthCheck() {
        return "Ping!! Success!";
    }


    @GetMapping("/task/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }
// curl -X POST -H "Content-type: application/json" -d "{\"taskName\" : \"mewww\", \"taskStatus\" : \"done\"}" "localhost:8080/task/submit"
    @PostMapping(value="/task/submit",
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> create(@RequestBody String taskJson) {
        System.out.println(taskJson);
        String response = taskService.createTask(taskJson);
        if(response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed. Please retry creation!!", HttpStatus.BAD_REQUEST);
    }
// curl -X PATCH "localhost:8080/task/{uuid}/complete"
    @PatchMapping(value = "/task/{uuid}/complete", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> completeTask(@PathVariable("uuid") String uuid) {
        String name = taskService.completeStatus(uuid);
        if(name != null)
            return new ResponseEntity<>("Successfully completed " + name, HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed. Please retry completion!!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/task/all/completed")
    public ResponseEntity<List<Task>> getAllCompletedTask() {
        return new ResponseEntity<>(taskService.getCompletedTasks(), HttpStatus.OK);
    }

    @GetMapping(value = "/task/all/incomplete")
    public ResponseEntity<List<Task>> getAllIncompleteTask() {
        return new ResponseEntity<>(taskService.getIncompleteTasks(), HttpStatus.OK);
    }

    @GetMapping(value = "/task/{uuid}")
    public ResponseEntity<String> getTask(@PathVariable String uuid) {
        String task = taskService.getTask(uuid);
        if(task != null)
            return new ResponseEntity<>(task, HttpStatus.OK);
        else
            return new ResponseEntity<>("Could not find object", HttpStatus.BAD_REQUEST);
    }
}
