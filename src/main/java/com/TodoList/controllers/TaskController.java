package com.TodoList.controllers;

import com.TodoList.dtos.requests.TaskRequest;
import com.TodoList.dtos.responses.TaskResponse;
import com.TodoList.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request) {
        return taskService.createTask(request.getUserId(), request);
    }

    @GetMapping("/user/{userId}")
    public List<TaskResponse> getTasksByUserId(@PathVariable("userId") String userId) {
        return taskService.getTasksByUserId(userId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("taskId") String taskId) {
        taskService.deleteTask(taskId);
    }


    @PutMapping("/{taskId}")
    public TaskResponse markCompleted(@PathVariable("taskId") String taskId) {
        return taskService.markCompleted(taskId);
    }

    @PutMapping("/task/{taskId}")
    public TaskResponse updateTask(@PathVariable("taskId") String taskId, @RequestBody TaskRequest request) {
        return taskService.updateTask(taskId, request);
    }
}
