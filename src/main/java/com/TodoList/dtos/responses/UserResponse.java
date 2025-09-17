package com.TodoList.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String email;
}
