package com.TodoList.services;

import com.TodoList.data.models.User;
import com.TodoList.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(User user) {
        if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null) {
            throw new IllegalArgumentException("All fields are required");
        }
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }
}
