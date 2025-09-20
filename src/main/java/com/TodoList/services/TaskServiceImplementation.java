package com.TodoList.services;

import com.TodoList.data.models.Task;
import com.TodoList.data.repositories.TaskRepository;
import com.TodoList.dtos.requests.TaskRequest;
import com.TodoList.dtos.responses.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(String userId, TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(false);
        task.setUserId(userId);
        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    private TaskResponse toResponse(Task task){
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }

    @Override
    public List<TaskResponse> getTasksByUser(String userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(String taskId, String userId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot delete task of another user");
        }
        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponse> searchTasks(String userId, String keyword) {
        return taskRepository.findByTitleContainingIgnoreCaseAndUserId(keyword, userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse markCompleted(String taskId, String userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot update task of another user");
        }
        task.setCompleted(true);
        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    @Override
    public TaskResponse getTaskById(String id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return toResponse(task);
    }

    // ✅ update/edit task
    @Override
    public TaskResponse updateTask(String taskId, String userId, TaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot update task of another user");
        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }
}
