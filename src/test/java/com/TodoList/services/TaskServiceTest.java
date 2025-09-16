package com.TodoList.services;

import com.TodoList.data.models.Task;
import com.TodoList.data.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*; // for assertEquals, assertTrue, etc.

@SpringBootTest
class TaskServiceTest {

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

        Task savedTask = taskService.createTask(task);

        assertNotNull(savedTask.getId());
        assertEquals("Buy groceries", savedTask.getTitle());
        assertTrue(taskRepository.existsById(savedTask.getId()));
    }

    @Test
    void markTaskAsCompleted() {
        Task task = new Task();
        task.setTitle("Read book");
        task.setDescription("clean code");

        Task savedTask = taskService.createTask(task);

        Task completedTask = taskService.markAsCompleted(savedTask.getId());

        assertTrue(completedTask.isCompleted());
    }
}

