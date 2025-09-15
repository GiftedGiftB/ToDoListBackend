package com.TodoList.data.repositories;

import com.TodoList.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User finByEmail(String email);
    boolean existsByEmail(String email);
}
