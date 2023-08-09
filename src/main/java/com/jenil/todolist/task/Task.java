package com.jenil.todolist.task;


import com.jenil.todolist.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Task {

    @Id
    private String taskId;

    private String taskName;

    private TaskStatus status;

    @DBRef
    private User user;

    public Task() {

    }

    public Task(String taskId, String taskName, TaskStatus status, User user) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
        this.user = user;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
