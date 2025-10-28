package com.abz.task_manager.controllers;

import com.abz.task_manager.domain.dto.TaskDto;
import com.abz.task_manager.domain.entities.Task;
import com.abz.task_manager.mappers.TaskMapper;
import com.abz.task_manager.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        return taskService.listTasks(id).stream().map(taskMapper :: toDto).toList();
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto, @PathVariable("task_list_id") UUID id) {
        Task createdTask = taskService.createTask(taskMapper.fromDto(taskDto), id);
        return taskMapper.toDto(createdTask);

    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID id) {
        Optional<Task> task = taskService.getTask(taskListId, id);
        return task.map(taskMapper::toDto);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID taskListId,  @PathVariable("task_id") UUID id, @RequestBody TaskDto taskDto) {
        Task updatedTask =  taskService.updateTask(taskListId, id, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "{task_id})")
    public void deleteTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID id) {
        taskService.deleteTask(taskListId, id);

    }


}
