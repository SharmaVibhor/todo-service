package com.example.todo_service;

import com.example.todo_service.domain.TodoItem;
import com.example.todo_service.service.TodoService;
import com.example.todo_service.domain.TodoStatus;
import com.example.todo_service.repository.TodoItemRepository;
import com.example.todo_service.service.exception.InvalidTodoStateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoItemRepository repository;

    @InjectMocks
    private TodoService service;

    @Test
    void shouldCreateTodoItem() {
        // given
        String description = "Test task";
        OffsetDateTime dueAt = OffsetDateTime.now().plusDays(1);

        when(repository.save(any(TodoItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        TodoItem result = service.addTodo(description, dueAt);

        // then
        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getStatus()).isEqualTo(TodoStatus.NOT_DONE);
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getDueAt()).isEqualTo(dueAt);
    }

    @Test
    void shouldNotAllowMarkingPastDueItemAsDone() {
        // given
        TodoItem item = new TodoItem(
                "Past task",
                OffsetDateTime.now().minusDays(1));
        item.markPastDue();

        when(repository.findById(1L))
                .thenReturn(Optional.of(item));

        // when / then
        assertThatThrownBy(() -> service.markDone(1L))
                .isInstanceOf(InvalidTodoStateException.class)
                .hasMessageContaining("Past-due");
    }

    @Test
    void shouldMarkItemsAsPastDue() {
        // given
        TodoItem item = new TodoItem(
                "Late task",
                OffsetDateTime.now().minusHours(1));

        when(repository.findByStatusAndDueAtBefore(eq(TodoStatus.NOT_DONE), any(OffsetDateTime.class)))
                .thenReturn(List.of(item));

        // when
        service.markPastDueItems();

        // then
        assertThat(item.getStatus()).isEqualTo(TodoStatus.PAST_DUE);
        verify(repository).save(eq(item));
    }
}
