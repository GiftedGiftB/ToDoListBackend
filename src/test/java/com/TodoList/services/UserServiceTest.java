package com.TodoList.services;

import com.TodoList.data.repositories.UserRepository;
import com.TodoList.dtos.requests.UserRequest;
import com.TodoList.dtos.responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase(){
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterNewUser(){
        UserRequest request = new UserRequest();
        request.setUsername("Gift");
        request.setEmail("gift@gmail.com");
        request.setPassword("12345");
        UserResponse savedUser = userService.register(request);
        assertEquals("gift@gmail.com", savedUser.getEmail());
        assertTrue(userRepository.existsByEmail("gift@gmail.com"));
    }

    @Test
    public void testLoginUser(){
        UserRequest request = new UserRequest();
        request.setUsername("Gift");
        request.setEmail("gift@gmail.com");
        request.setPassword("12345");
        userService.register(request);
        UserResponse response = userService.login("gift@gmail.com", "12345");
        assertEquals("gift@gmail.com", response.getEmail());
    }

    @Test
    public void testLoginInvalidPassword() {
        UserRequest request = new UserRequest();
        request.setUsername("Gift");
        request.setEmail("gift@gmail.com");
        request.setPassword("12345");
        userService.register(request);
        Exception ex = assertThrows(RuntimeException.class, () -> userService.login("gift@gmail.com", "wrongpass"));
        assertTrue(ex.getMessage().contains("Invalid password"));
    }

}