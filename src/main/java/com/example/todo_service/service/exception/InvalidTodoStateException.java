package com.example.todo_service.service.exception;

public class InvalidTodoStateException extends RuntimeException {

    public InvalidTodoStateException(String message) {
        super(message);
    }
}
