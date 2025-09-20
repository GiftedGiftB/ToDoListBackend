package com.TodoList.services;

import com.TodoList.dtos.requests.UserRequest;
import com.TodoList.dtos.responses.UserResponse;

public interface UserService {
    UserResponse register(UserRequest request);
    UserResponse login(String email, String password);
}
