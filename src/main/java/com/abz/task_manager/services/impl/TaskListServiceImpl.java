package com.abz.task_manager.services.impl;

import com.abz.task_manager.controllers.GlobalExceptionHandler;
import com.abz.task_manager.domain.entities.TaskList;
import com.abz.task_manager.repositories.TaskListRepositories;
import com.abz.task_manager.services.TaskListService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
        return taskListRepositories.save(new TaskList(null, taskList.getTitle(), taskList.getDescription(), now, now, null));


    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if (taskList.getId() == null) {
            throw new IllegalArgumentException("Task list id is required");

        }
        if (taskList.getTitle() == null || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title is required");
        }
        if(taskList.getId() != taskListId) {
            throw new IllegalArgumentException("attempting to change task list id");
        }
        LocalDateTime now = LocalDateTime.now();
        TaskList existingTaskList = taskListRepositories.findById(taskListId).orElseThrow(() -> new IllegalArgumentException("task list id not found"));
        return taskListRepositories.save(new TaskList(taskListId, taskList.getTitle(), taskList.getDescription(), existingTaskList.getCreated(), now, taskList.getTasks()));

    }

    @Override
    public Optional<TaskList> findTaskListById(UUID id) {
        Optional<TaskList> taskList = taskListRepositories.findById(id);


        return taskList;



    }

    @Override
    public void deleteTaskListById(UUID id) {
        taskListRepositories.deleteById(id);

    }
}
