package com.example.todo_service.service;

import com.example.todo_service.domain.TodoItem;
import com.example.todo_service.domain.TodoStatus;
import com.example.todo_service.repository.TodoItemRepository;
import com.example.todo_service.service.exception.InvalidTodoStateException;
import com.example.todo_service.service.exception.TodoNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class TodoService {

    private final TodoItemRepository repository;

    public TodoService(TodoItemRepository repository) {
        this.repository = repository;
    }

    public TodoItem addTodo(String description, OffsetDateTime dueAt) {
        TodoItem item = new TodoItem(description, dueAt);
        return repository.save(item);
    }

    @Transactional(readOnly = true)
    public TodoItem getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<TodoItem> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TodoItem> getNotDone() {
        return repository.findByStatus(TodoStatus.NOT_DONE);
    }

    public void changeDescription(Long id, String newDescription) {
        TodoItem item = getById(id);
        try {
            item.changeDescription(newDescription);
        } catch (IllegalStateException e) {
            throw new InvalidTodoStateException(e.getMessage());
        }
    }

    public void markDone(Long id) {
        TodoItem item = getById(id);
        try {
            item.markDone();
        } catch (IllegalStateException e) {
            throw new InvalidTodoStateException(e.getMessage());
        }
    }

    public void markNotDone(Long id) {
        TodoItem item = getById(id);
        try {
            item.markNotDone();
        } catch (IllegalStateException e) {
            throw new InvalidTodoStateException(e.getMessage());
        }
    }

    public void markPastDueItems() {
        List<TodoItem> overdueItems = repository.findByStatusAndDueAtBefore(
                TodoStatus.NOT_DONE,
                OffsetDateTime.now());

        overdueItems.forEach(TodoItem::markPastDue);
    }
}
