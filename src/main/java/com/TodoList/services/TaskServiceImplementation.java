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
        return new TaskResponse(saved.getId(), saved.getTitle(), saved.getDescription(), saved.isCompleted());
    }

    @Override
    public List<TaskResponse> getTasksByUserId(String userId) {
        return taskRepository.findByUserId(userId).stream().map(t -> new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.isCompleted())).toList();
    }

    @Override
    public void deleteTask(String taskId){
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskResponse markCompleted(String taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(true);
        Task updated = taskRepository.save(task);
        return new TaskResponse(updated.getId(),updated.getTitle(), updated.getDescription(), updated.isCompleted());
    }

    @Override
    public TaskResponse getTaskById(String id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }

    @Override
    public TaskResponse updateTask(String taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        Task updated = taskRepository.save(task);
        return new TaskResponse(updated.getId(), updated.getTitle(), updated.getDescription(), updated.isCompleted());
    }
}
