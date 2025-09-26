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
    public void testThatTaskCanBeAdded() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Community hangout");
        request.setDescription("Hangout");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        assertNotNull(response.getId());
        assertEquals("Community hangout", response.getTitle()); // fixed case
        List<TaskResponse> tasks = taskService.getTasksByUserId(savedUser.getId());
        assertFalse(tasks.isEmpty());
    }

    @Test
    public void testThatTaskCanBeMarkedAsCompleted() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Gift");
        request.setDescription("Gift task");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        assertFalse(response.isCompleted());
        TaskResponse completed = taskService.markCompleted(response.getId(),true);
        assertTrue(completed.isCompleted());

    }
    @Test
    public void testThatTaskCanBeGottenByTaskId() {
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
        request.setTitle("project1");
        request.setDescription("To-do App");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        TaskRequest update = new TaskRequest();
        update.setTitle("PhaseGate");
        update.setDescription("Two project to go");
        TaskResponse updated = taskService.updateTask(response.getId(), update);
        assertEquals("PhaseGate", updated.getTitle());
        assertEquals("Two project to go", updated.getDescription());
    }

    @Test
    public void testThatTaskCanBeDeleted() {
        TaskRequest request = new TaskRequest();
        request.setTitle("project2");
        request.setDescription("Kahoot");
        TaskResponse response = taskService.createTask(savedUser.getId(), request);
        taskService.deleteTask(response.getId());
        List<TaskResponse> tasks = taskService.getTasksByUserId(savedUser.getId());
        assertTrue(tasks.stream().noneMatch(t -> t.getId().equals(response.getId())));
    }

}