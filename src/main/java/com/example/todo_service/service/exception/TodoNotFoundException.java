package com.example.todo_service.service.exception;

public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(Long id) {
        super("Todo item not found: " + id);
    }
}
