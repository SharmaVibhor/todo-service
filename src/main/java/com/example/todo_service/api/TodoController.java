package com.example.todo_service.api;

import com.example.todo_service.api.dto.CreateTodoRequest;
import com.example.todo_service.api.dto.TodoResponse;
import com.example.todo_service.api.dto.UpdateDescriptionRequest;
import com.example.todo_service.domain.TodoItem;
import com.example.todo_service.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse create(@RequestBody CreateTodoRequest request) {
        TodoItem item = service.addTodo(
                request.getDescription(),
                request.getDueAt());
        return TodoResponse.from(item);
    }

    @GetMapping
    public List<TodoResponse> getAll(
            @RequestParam(defaultValue = "false") boolean includeDone) {
        List<TodoItem> items = includeDone
                ? service.getAll()
                : service.getNotDone();

        return items.stream()
                .map(TodoResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TodoResponse getById(@PathVariable Long id) {
        return TodoResponse.from(service.getById(id));
    }

    @PatchMapping("/{id}/description")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDescription(
            @PathVariable Long id,
            @RequestBody UpdateDescriptionRequest request) {
        service.changeDescription(id, request.getDescription());
    }

    @PatchMapping("/{id}/done")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markDone(@PathVariable Long id) {
        service.markDone(id);
    }

    @PatchMapping("/{id}/not-done")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markNotDone(@PathVariable Long id) {
        service.markNotDone(id);
    }
}
