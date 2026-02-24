package com.abz.task_manager.services.impl;

import com.abz.task_manager.domain.entities.TaskList;
import com.abz.task_manager.repositories.TaskListRepositories;
import com.abz.task_manager.services.TaskListService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepositories taskListRepositories;

    public TaskListServiceImpl(TaskListRepositories taskListRepositories) {
        this.taskListRepositories = taskListRepositories;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepositories.findAll();
    }


    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getId() != null) {
            throw new IllegalArgumentException("Task list already exists");

        }
        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title is required");

        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepositories.save(new TaskList(null, taskList.getTitle(), taskList.getDescription(), now, now, new ArrayList<>()));


    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title is required");
        }
        if (taskList.getId() != null && !taskList.getId().equals(taskListId)) {
            throw new IllegalArgumentException("attempting to change task list id");
        }
        LocalDateTime now = LocalDateTime.now();
        TaskList existingTaskList = taskListRepositories.findById(taskListId)
                .orElseThrow(() -> new EntityNotFoundException("task list id not found"));
        return taskListRepositories.save(new TaskList(taskListId, taskList.getTitle(), taskList.getDescription(), existingTaskList.getCreated(), now, taskList.getTasks()));

    }

    @Override
    public Optional<TaskList> findTaskListById(UUID id) {
        return taskListRepositories.findById(id);
    }

    @Override
    public void deleteTaskListById(UUID id) {
        if (!taskListRepositories.existsById(id)) {
            throw new EntityNotFoundException("task list id not found");
        }
        taskListRepositories.deleteById(id);
    }
}
