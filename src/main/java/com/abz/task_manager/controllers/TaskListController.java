package com.abz.task_manager.controllers;

import com.abz.task_manager.domain.dto.TaskListDto;
import com.abz.task_manager.domain.entities.TaskList;
import com.abz.task_manager.mappers.TaskListMapper;
import com.abz.task_manager.services.TaskListService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = "/task-lists")
@RestController
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;
    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists().stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskListDto createTaskList(@Valid @RequestBody TaskListDto taskListDto) {
        TaskList taskListCreated = taskListService.createTaskList(taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(taskListCreated);
    }

    @GetMapping(path = "/{task_list_id}")
    public TaskListDto getTaskListById(@PathVariable("task_list_id") UUID id) {
        TaskList taskList = taskListService.findTaskListById(id)
                .orElseThrow(() -> new EntityNotFoundException("task list id not found"));
        return taskListMapper.toDto(taskList);
    }

    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID id, @Valid @RequestBody TaskListDto taskListDto) {
        TaskList taskList = taskListService.updateTaskList(id, taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(taskList);
    }

    @DeleteMapping(path = "/{task_list_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskList(@PathVariable("task_list_id") UUID id) {
        taskListService.deleteTaskListById(id);
    }
}
