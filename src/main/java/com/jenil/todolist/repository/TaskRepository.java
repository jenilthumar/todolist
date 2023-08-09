package com.jenil.todolist.repository;

import com.jenil.todolist.task.Task;
import com.jenil.todolist.task.TaskStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {


    @Query("{status:'?0'}")
    List<Task> findByStatus(TaskStatus status);

    List<Task> sortByTaskName(String taskName, Sort sort);

}
