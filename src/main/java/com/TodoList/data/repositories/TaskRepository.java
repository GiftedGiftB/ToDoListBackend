package com.TodoList.data.repositories;

import com.TodoList.data.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task,String> {
    List<Task> findByUserId(String userId);
    //List<Task> findByTitleContainingIgnoreCaseAndUserId(String title, String userId);
}