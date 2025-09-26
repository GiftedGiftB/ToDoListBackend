package com.TodoList.services;

import com.TodoList.data.models.User;
import com.TodoList.data.repositories.UserRepository;
import com.TodoList.dtos.requests.UserRequest;
import com.TodoList.dtos.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserResponse register(UserRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email has been used before!");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail());
    }

    @Override
    public UserResponse login(String email, String password){
        User result = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (!result.getPassword().equals(password)){
            throw new RuntimeException("Invalid password");
        }
        return new UserResponse(result.getId(), result.getUsername(), result.getEmail());
    }

}
