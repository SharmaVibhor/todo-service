package com.example.todo_service.api.dto;

import java.time.OffsetDateTime;

public class CreateTodoRequest {

    private String description;
    private OffsetDateTime dueAt;

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getDueAt() {
        return dueAt;
    }
}
