package com.TodoList.dtos.requests;

public class CompleteRequest {
    private boolean completed;
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
