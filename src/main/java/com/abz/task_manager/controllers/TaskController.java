package com.abz.task_manager.controllers;

import com.abz.task_manager.domain.dto.TaskDto;
import com.abz.task_manager.domain.entities.Task;
import com.abz.task_manager.mappers.TaskMapper;
import com.abz.task_manager.services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> getTasks(@PathVariable("task_list_id") UUID id) {
        return taskService.listTasks(id).stream().map(taskMapper::toDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDto, @PathVariable("task_list_id") UUID id) {
        Task createdTask = taskService.createTask(taskMapper.fromDto(taskDto), id);
        return taskMapper.toDto(createdTask);
    }

    @GetMapping(path = "/{task_id}")
    public TaskDto getTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID id) {
        Task task = taskService.getTask(taskListId, id)
                .orElseThrow(() -> new EntityNotFoundException("Task does not exist"));
        return taskMapper.toDto(task);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID id, @Valid @RequestBody TaskDto taskDto) {
        Task updatedTask = taskService.updateTask(taskListId, id, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID id) {
        taskService.deleteTask(taskListId, id);
    }
}
