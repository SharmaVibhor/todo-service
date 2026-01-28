package com.example.todo_service.api.dto;

import com.example.todo_service.domain.TodoItem;
import com.example.todo_service.domain.TodoStatus;

import java.time.OffsetDateTime;

public class TodoResponse {

    private Long id;
    private String description;
    private TodoStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime dueAt;
    private OffsetDateTime doneAt;

    public static TodoResponse from(TodoItem item) {
        TodoResponse response = new TodoResponse();
        response.id = item.getId();
        response.description = item.getDescription();
        response.status = item.getStatus();
        response.createdAt = item.getCreatedAt();
        response.dueAt = item.getDueAt();
        response.doneAt = item.getDoneAt();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getDueAt() {
        return dueAt;
    }

    public OffsetDateTime getDoneAt() {
        return doneAt;
    }
}
