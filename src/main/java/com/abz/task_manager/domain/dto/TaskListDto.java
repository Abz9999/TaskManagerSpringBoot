package com.abz.task_manager.domain.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record TaskListDto(
        UUID id,
        @NotBlank(message = "Task list title is required")
        String title,
        String description,
        List<TaskDto> tasks
) {
}
