package com.TodoList.data.repositories;

import com.TodoList.data.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task,String> {
}
