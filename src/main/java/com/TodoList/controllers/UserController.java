package com.TodoList.controllers;

import com.TodoList.dtos.requests.UserRequest;
import com.TodoList.dtos.responses.UserResponse;
import com.TodoList.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        return userService.register(request);
    }

@PostMapping("/login")
public UserResponse login(@RequestBody UserRequest request) {
    return userService.login(request.getEmail(), request.getPassword());
}

}
