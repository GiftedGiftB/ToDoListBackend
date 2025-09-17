package com.TodoList.services;

import com.TodoList.data.models.Task;
import com.TodoList.data.models.User;
import com.TodoList.data.repositories.TaskRepository;
import com.TodoList.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void cleanDatabase() {
        taskRepository.deleteAll();
    }

    @Test
    void addTask() {
        Task task = new Task();
        task.setTitle("Buy groceries");
        task.setDescription("milk, bread, eggs");
        Task savedTask = taskService.addTask(task);
        assertNotNull(savedTask.getId());
        assertEquals("Buy groceries", savedTask.getTitle());
        assertTrue(taskRepository.existsById(savedTask.getId()));
    }

    @Test
    void markTaskAsCompleted() {
        User user = new User();
        user.setPassword("12345");
        user.setEmail("gift@gmail.com");
        User savedUser = userRepository.save(user);
        Task task = new Task();
        task.setTitle("Read book");
        task.setDescription("clean code");
        task.setUserId(user.getId());
        Task savedTask = taskService.addTask(task);
        Task completedTask = taskService.markCompleted(savedTask.getId(), savedUser.getId());
        assertTrue(completedTask.isCompleted());
    }
}

