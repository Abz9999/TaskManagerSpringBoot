package com.abz.task_manager.domain.dto;

import com.abz.task_manager.domain.entities.TaskPriority;
import com.abz.task_manager.domain.entities.TaskStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        @NotBlank(message = "Task title is required")
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
