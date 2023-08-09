package com.jenil.todolist.task;

import com.jenil.todolist.repository.TaskRepository;
import com.jenil.todolist.repository.UserRepository;
import com.jenil.todolist.user.User;
import com.jenil.todolist.user.UserNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.jenil.todolist.task.TaskStatus.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
public class TaskController {

    @Autowired
    private final TaskRepository taskRepo;

    @Autowired
    private final UserRepository userRepo;

    public TaskController(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }


    @GetMapping("/users/{id}/tasks")
    public List<Task> getAllTask(@PathVariable String id) {
        return taskRepo.findAll();
    }

    @PostMapping("/users/{id}/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @PathVariable String id) {
        User user = userRepo.findById(id).
                orElseThrow(() -> new UserNotFoundException("Id: " + id));

        task.setUser(user);
        taskRepo.save(task);

        return ResponseEntity.ok(task);
    }

    @GetMapping("/users/{id}/tasks/{taskId}")
    public Task getTaskById(@PathVariable String taskId){
        return taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("TaskId: " + taskId));
    }

    @GetMapping("/users/{id}/tasks/pending")
    public List<Task> getPendingTask(@PathVariable String id) {

        if(userRepo.findById(id).isPresent()) {
            return taskRepo.findByStatus(PENDING);
        }
        else if(userRepo.findById(id).isEmpty()){
            throw new UserNotFoundException("User not found.");
        }
        else{
            throw new TaskNotFoundException("Task not found.");
        }
    }

    @GetMapping("/users/{id}/tasks/inprogress")
    public List<Task> getInProgressTask(@PathVariable String id) {

        if(userRepo.findById(id).isPresent()) {
            return taskRepo.findByStatus(IN_PROGRESS);
        }
        else if(userRepo.findById(id).isEmpty()){
            throw new UserNotFoundException("User not found.");
        }
        else{
            throw new TaskNotFoundException("Task not found.");
        }
    }

    @GetMapping("/users/{id}/tasks/completed")
    public List<Task> getCompletedTask(@PathVariable String id) {

        if(userRepo.findById(id).isPresent()) {
            return taskRepo.findByStatus(COMPLETED);
        }
        else if(userRepo.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }
        else{
            throw new TaskNotFoundException("Task not found.");
        }
    }


    @PutMapping("/users/{id}/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable String id, String taskId) {
        User user = userRepo.findById(id).
                orElseThrow(() -> new UserNotFoundException("Id: " + id));

        task.setUser(user);
        //task.setStatus(PENDING);
        taskRepo.save(task);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/users/{id}/tasks")
    public ResponseEntity<?> deleteAllTask(@PathVariable String id) {

        if(userRepo.findById(id).isPresent()) {
            taskRepo.deleteAll();
            return ResponseEntity.ok("All tasks deleted.");
        }
        else {
            userRepo.findById(id);
            return ResponseEntity.badRequest().body("Bad request.");
        }
    }

    @DeleteMapping("/users/{id}/tasks/{taskId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable String taskId) {

        if(taskRepo.findById(taskId).isPresent()) {
            taskRepo.deleteById(taskId);
            return ResponseEntity.ok("Task deleted.");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
    }

    @GetMapping("/users/{id}/tasks/sort=name")
    public List<Task> sortByNameTasks(@PathVariable String id) {

        return taskRepo.findAll(Sort.by(DESC, "taskName"));

    }
}
