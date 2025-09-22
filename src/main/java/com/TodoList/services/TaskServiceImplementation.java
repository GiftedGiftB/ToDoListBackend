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
    public List<TaskResponse> getTasksByUserId(String userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        System.out.println("Tasks for user " + userId + ": " + tasks.size());
        for (Task t : tasks) {
            System.out.println("Task id: " + t.getId() + " title: " + t.getTitle());
        }
        return tasks.stream().map(this::toResponse).collect(Collectors.toList());
    }


    @Override
    public void deleteTask(String taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }


    @Override
    public TaskResponse markCompleted(String taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(true);
        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    @Override
    public TaskResponse getTaskById(String id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        return toResponse(task);
    }

    @Override
    public TaskResponse updateTask(String taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }
}
