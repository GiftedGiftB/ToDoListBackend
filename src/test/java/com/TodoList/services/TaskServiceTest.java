package com.TodoList.services;

import com.TodoList.data.models.Task;
import com.TodoList.data.models.User;
import com.TodoList.data.repositories.TaskRepository;
import com.TodoList.data.repositories.UserRepository;
import com.TodoList.dtos.requests.TaskRequest;
import com.TodoList.dtos.responses.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

    TaskRequest request;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        request = new TaskRequest();
        request.setTitle("gift");
        request.setDescription("gift");
        TaskResponse response = new TaskResponse();
    }

    @Test
    void addTask() {
        Task task = new Task();
        task.setTitle("Community hangout");
        task.setDescription("Hangout");
        Task savedTask = taskService.addTask(task);
        assertNotNull(savedTask.getId());
        assertEquals("Community hangout", savedTask.getTitle());
        assertTrue(taskRepository.existsById(savedTask.getId()));
    }

    @Test
    void markTaskAsCompleted() {
        User user = new User();
        user.setPassword("12345");
        user.setEmail("gift@gmail.com");
        User savedUser = userRepository.save(user);
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUserId(user.getId());
        Task savedTask = taskService.addTask(task);
        assertFalse(savedTask.isCompleted());
        Task completedTask = taskService.markCompleted(savedTask.getId(), savedUser.getId());
        assertTrue(completedTask.isCompleted());
    }
    
}

