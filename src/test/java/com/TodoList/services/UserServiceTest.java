package com.TodoList.services;

import com.TodoList.data.models.User;
import com.TodoList.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        User user = new User();
        user.setPassword("12345");
        user.setEmail("gift@gmail.com");

        User savedUser = userRepository.save(user);
        assertEquals("gift@gmail.com", savedUser.getEmail());
        assertTrue(userRepository.existsByEmail("gift@gmail.com"));
    }


}