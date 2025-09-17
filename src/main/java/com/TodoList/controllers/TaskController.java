package com.TodoList.controllers;

import com.TodoList.data.models.Task;
import com.TodoList.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/add")
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }


    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable String userId) {
        return taskService.getTasksByUser(userId);
    }


    @DeleteMapping("/{taskId}/user/{userId}")
    public String deleteTask(@PathVariable String taskId, @PathVariable String userId) {
        taskService.deleteTask(taskId, userId);
        return "Task deleted successfully!";
    }


    @GetMapping("/search/user/{userId}")
    public List<Task> searchTasks(@PathVariable String userId, @RequestParam String keyword) {
        return taskService.searchTasks(userId, keyword);
    }

    @PutMapping("/{taskId}/complete/user/{userId}")
    public Task markCompleted(@PathVariable String taskId, @PathVariable String userId) {
        return taskService.markCompleted(taskId,userId);
    }
}
