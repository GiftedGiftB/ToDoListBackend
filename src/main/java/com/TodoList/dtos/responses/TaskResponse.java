package com.TodoList.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private boolean completed;
}