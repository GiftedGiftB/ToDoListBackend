package com.TodoList.services;

import com.TodoList.data.models.Task;
import com.TodoList.data.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task creteTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id){
        return taskRepository.findById(id);
    }

    public void deleteTask(String id){
        taskRepository.deleteById(id);
    }

    public Optional<Task> markTaskAsCompleted(String id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setCompleted(true);
            taskRepository.save(task);
            return Optional.of(task);
        } else{
            return Optional.empty();
        }
    }
}
