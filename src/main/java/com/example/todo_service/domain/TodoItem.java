package com.example.todo_service.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "todo_items")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime dueAt;

    private OffsetDateTime doneAt;

    protected TodoItem() {
        // for JPA
    }

    public TodoItem(String description, OffsetDateTime dueAt) {
        this.description = description;
        this.dueAt = dueAt;
        this.status = TodoStatus.NOT_DONE;
        this.createdAt = OffsetDateTime.now();
    }

    /* ===== Domain behavior ===== */

    public void markDone() {
        if (status == TodoStatus.PAST_DUE) {
            throw new IllegalStateException("Past-due items cannot be modified");
        }
        this.status = TodoStatus.DONE;
        this.doneAt = OffsetDateTime.now();
    }

    public void markNotDone() {
        if (status == TodoStatus.PAST_DUE) {
            throw new IllegalStateException("Past-due items cannot be modified");
        }
        this.status = TodoStatus.NOT_DONE;
        this.doneAt = null;
    }

    public void changeDescription(String newDescription) {
        if (status == TodoStatus.PAST_DUE) {
            throw new IllegalStateException("Past-due items cannot be modified");
        }
        this.description = newDescription;
    }

    public void markPastDue() {
        if (this.status != TodoStatus.PAST_DUE) {
            this.status = TodoStatus.PAST_DUE;
        }
    }

    /* ===== Getters ===== */

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

    /* ===== Equality ===== */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TodoItem))
            return false;
        TodoItem todoItem = (TodoItem) o;
        return id != null && id.equals(todoItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
