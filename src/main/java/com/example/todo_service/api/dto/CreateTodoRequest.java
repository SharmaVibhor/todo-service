package com.example.todo_service.api.dto;

import java.time.OffsetDateTime;

public class CreateTodoRequest {

    private String description;
    private OffsetDateTime dueAt;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueAt(OffsetDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getDueAt() {
        return dueAt;
    }
}
