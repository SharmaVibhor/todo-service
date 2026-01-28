package com.example.todo_service.repository;

import com.example.todo_service.domain.TodoItem;
import com.example.todo_service.domain.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findByStatus(TodoStatus status);

    List<TodoItem> findByStatusAndDueAtBefore(
            TodoStatus status,
            OffsetDateTime dateTime
    );
}
