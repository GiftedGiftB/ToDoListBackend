package com.TodoList.controllers;

import com.TodoList.dtos.requests.TaskRequest;
import com.TodoList.dtos.responses.TaskResponse;
import com.TodoList.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request) {
        return taskService.createTask(request.getUserId(), request);
    }


    @GetMapping("/user/{userId}")
    public List<TaskResponse> getTasksByUserId(@PathVariable("userId") String userId) {
        return taskService.getTasksByUserId(userId);
    }

    @DeleteMapping("/task{taskId}/user/{userId}")
    public void deleteTask(@PathVariable("taskId") String taskId, @PathVariable("userId") String userId) {
        taskService.deleteTask(taskId, userId);
    }


    @PutMapping("/{taskId}/complete/user/{userId}")
    public TaskResponse markCompleted(@PathVariable String taskId, @PathVariable String userId) {
        return taskService.markCompleted(taskId,userId);
    }

    @PutMapping("/task/{taskId}/user/{userId}")
    public TaskResponse updateTask(@PathVariable("taskId") String taskId, @PathVariable("userId") String userId, @RequestBody TaskRequest request) {
        return taskService.updateTask(taskId, userId, request);
    }
}
