package com.TodoList.services;


import com.TodoList.data.models.Task;
import com.TodoList.data.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void cleanDatabase(){
        taskRepository.deleteAll();
    }

    @Test
    public void addTask(){
        Task task = new Task();
        task.setTitle("Buy groceries");
        task.setDescription("milk, bread, eggs");
        Task savedTask = taskService.save(task);
        Assertions.assertNotNull(savedTask.getId());
        Assertions.assertEquals("Buy groceries", savedTask.getTitle());
        Assertions.assertTrue(taskRepository.existsById(savedTask.getId()));
    }

    @Test
    public void markTaskAsCompleted(){
        Task task = new Task();
        task.setTitle("Read book");
        task.setDescription("clean code");
        Task savedTask = taskService.save(task);
        Task completedTask = taskService.markAsCompleted(savedTask.getId());
        Assertions.assertTrue(completedTask.isCompleted());
    }

}
