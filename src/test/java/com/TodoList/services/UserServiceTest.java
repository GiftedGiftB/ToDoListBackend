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

public class UserServiceTest{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase(){
        userRepository.deleteAll();
    }

    @Test
    public void testThatNewUserCanBeRegistered(){
        UserRequest request = new UserRequest();
        request.setUsername("Gift");
        request.setEmail("gift@gmail.com");
        request.setPassword("12345");
        UserResponse savedUser = userService.register(request);
        assertEquals("gift@gmail.com", savedUser.getEmail());
        assertTrue(userRepository.existsByEmail("gift@gmail.com"));
    }

    @Test
    public void testThatUserCanLogin(){
        UserRequest request = new UserRequest();
        request.setUsername("Gift");
        request.setEmail("gift@gmail.com");
        request.setPassword("12345");
        userService.register(request);
        UserResponse response = userService.login("gift@gmail.com", "12345");
        assertEquals("gift@gmail.com", response.getEmail());
    }

    @Test
    public void testThatUserCanLoginWithCorrectPassword() {
        UserRequest request = new UserRequest();
        request.setUsername("Gift");
        request.setEmail("gift@gmail.com");
        request.setPassword("12345");
        userService.register(request);
        UserResponse user = userService.login("gift@gmail.com", "12345");
        assertNotNull(user);
        assertEquals("gift@gmail.com", user.getEmail());
    }

    @Test
    public void testThatLoginWithWrongPasswordThrowsException() {
        UserRequest request = new UserRequest();
        request.setUsername("Gift");
        request.setEmail("gift@gmail.com");
        request.setPassword("12345");
        userService.register(request);
        Exception exception = assertThrows(RuntimeException.class, () -> userService.login("gift@gmail.com", "wrongpassword"));
        assertTrue(exception.getMessage().contains("Invalid password"));
    }



}