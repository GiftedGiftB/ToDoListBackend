package com.TodoList.services;

import com.TodoList.data.models.Task;
import com.TodoList.data.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task addTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        task.setCompleted(false); // default not completed
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(String userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        if (tasks.isEmpty()) {
            throw new RuntimeException("No tasks found for this user");
        }
        return tasks;
    }

    public void deleteTask(String taskId, String userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot delete task of another user");
        }
        taskRepository.delete(task);
    }

    public List<Task> searchTasks(String userId, String keyword) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCaseAndUserId(keyword, userId);
        if (tasks.isEmpty()) {
            throw new RuntimeException("No matching tasks found");
        }
        return tasks;
    }

    public Task markCompleted(String taskId, String userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot update task of another user");
        }
        task.setCompleted(true);
        return taskRepository.save(task);
    }
}
