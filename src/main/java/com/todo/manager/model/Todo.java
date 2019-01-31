package com.todo.manager.model;

import com.todo.manager.enums.TodoCategory;
import com.todo.manager.enums.TodoStatus;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private TodoCategory category;
    private String description;
    private Instant dueDate;
    private TodoStatus status;

    public Todo(){}

    public Todo(String title, TodoCategory category, String description, Instant dueDate, TodoStatus status) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TodoCategory getCategory() {
        return category;
    }

    public void setCategory(TodoCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
