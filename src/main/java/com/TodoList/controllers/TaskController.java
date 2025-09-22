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
    public List<TaskResponse> getTasksByUser(@PathVariable String userId) {
        return taskService.getTasksByUser(userId);
    }

    @DeleteMapping("/{taskId}/user/{userId}")
    public void deleteTask(@PathVariable String taskId, @PathVariable String userId) {
        taskService.deleteTask(taskId, userId);
    }

    @GetMapping("/search/user/{userId}")
    public List<TaskResponse> searchTasks(@PathVariable String userId, @RequestParam String keyword) {
        return taskService.searchTasks(userId, keyword);
    }

    @PutMapping("/{taskId}/complete/user/{userId}")
    public TaskResponse markCompleted(@PathVariable String taskId, @PathVariable String userId) {
        return taskService.markCompleted(taskId,userId);
    }

    @PutMapping("/{taskId}/user/{userId}")
    public TaskResponse updateTask(@PathVariable String taskId, @PathVariable String userId, @RequestBody TaskRequest request) {
        return taskService.updateTask(taskId, userId, request);
    }
}
