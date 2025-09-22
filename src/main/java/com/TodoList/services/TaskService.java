package com.TodoList.services;

import com.TodoList.dtos.requests.TaskRequest;
import com.TodoList.dtos.responses.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(String userId, TaskRequest request);
    List<TaskResponse> getTasksByUserId(String userId);
    void deleteTask(String taskId, String userId);
    TaskResponse markCompleted(String taskId, String userId);
    TaskResponse getTaskById(String taskId);
    TaskResponse updateTask(String taskId, String userId, TaskRequest request);
}
