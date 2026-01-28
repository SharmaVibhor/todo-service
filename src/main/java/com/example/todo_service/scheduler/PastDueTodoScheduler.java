package com.example.todo_service.scheduler;

import com.example.todo_service.service.TodoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PastDueTodoScheduler {

    private final TodoService todoService;

    public PastDueTodoScheduler(TodoService todoService) {
        this.todoService = todoService;
    }

    /*
     * Runs every minute.
     */
    @Scheduled(fixedRate = 60_000)
    public void markPastDueTodos() {
        todoService.markPastDueItems();
    }
}
