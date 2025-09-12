package com.TodoList.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String userName;
    private String email;
}
