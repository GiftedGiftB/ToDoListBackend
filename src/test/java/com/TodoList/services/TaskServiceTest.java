package com.TodoList.services;

import com.TodoList.data.models.User;
import com.TodoList.data.repositories.UserRepository;
import com.TodoList.dtos.requests.TaskRequest;
import com.TodoList.dtos.responses.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    private User savedUser;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("Gift");
        user.setEmail("gift@gmail.com");
        user.setPassword("12345");
        savedUser = userRepository.save(user);
    }

    @Test
    public void addTask() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Community hangout");
        request.setDescription("Hangout");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        assertNotNull(response.getId());
        assertEquals("Community hangout", response.getTitle()); // fixed case
        List<TaskResponse> tasks = taskService.getTasksByUser(savedUser.getId());
        assertFalse(tasks.isEmpty());
    }

    @Test
    public void markTaskAsCompleted() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Gift");
        request.setDescription("Gift task");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        assertFalse(response.isCompleted());
        TaskResponse completed = taskService.markCompleted(response.getId(), savedUser.getId());
        assertTrue(completed.isCompleted());

    }
    @Test
    public void getTaskById() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Gift");
        request.setDescription("Gift task");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        TaskResponse found = taskService.getTaskById(response.getId());
        assertNotNull(found);
        assertEquals("Gift", found.getTitle());
    }

    @Test
    void updateTask() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Old Title");
        request.setDescription("Old Desc");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        TaskRequest update = new TaskRequest();
        update.setTitle("New Title");
        update.setDescription("New Desc");
        TaskResponse updated = taskService.updateTask(response.getId(), savedUser.getId(), update);
        assertEquals("New Title", updated.getTitle());
        assertEquals("New Desc", updated.getDescription());
    }

    @Test
    public void deleteTask() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Task to delete");
        request.setDescription("Delete me");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        taskService.deleteTask(response.getId(), savedUser.getId());
        List<TaskResponse> tasks = taskService.getTasksByUser(savedUser.getId());
        assertTrue(tasks.stream().noneMatch(t -> t.getId().equals(response.getId())));
    }

    @Test
    void searchTasks() {
        TaskRequest task1 = new TaskRequest();
        task1.setTitle("Shopping list");
        task1.setDescription("Groceries");
        taskService.createTask(savedUser.getId(), task1);
        TaskRequest task2 = new TaskRequest();
        task2.setTitle("Work project");
        task2.setDescription("Finish code");
        taskService.createTask(savedUser.getId(), task2);
        List<TaskResponse> found = taskService.searchTasks(savedUser.getId(), "shop");
        assertFalse(found.isEmpty());
        assertTrue(found.getFirst().getTitle().toLowerCase().contains("shop"));
    }
}