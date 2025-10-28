package com.abz.task_manager.services.impl;

import com.abz.task_manager.domain.entities.Task;
import com.abz.task_manager.domain.entities.TaskList;
import com.abz.task_manager.domain.entities.TaskPriority;
import com.abz.task_manager.domain.entities.TaskStatus;
import com.abz.task_manager.repositories.TaskListRepositories;
import com.abz.task_manager.repositories.TaskRepository;
import com.abz.task_manager.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;
    private final TaskListRepositories taskListRepositories;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepositories taskListRepositories) {
        this.taskRepository = taskRepository;
        this.taskListRepositories = taskListRepositories;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }
    @Transactional
    @Override
    public Task createTask(Task task, UUID taskListId) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Task already exists");

        }
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title is required");

        }
        LocalDateTime now = LocalDateTime.now();
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;
        TaskList taskList = taskListRepositories.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Task list does not exist"));


        return taskRepository.save(new Task(null, task.getTitle(), task.getDescription(), task.getDueDate(), taskStatus, taskPriority, now, now, taskList));


    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID id) {
        return taskRepository.findByTaskListIdAndId(taskListId, id);
    }

    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task id is required");
        }

        if(task.getId() != taskId){
            throw new IllegalArgumentException("changing task id not allowed");


        }
        if(task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title is required");


        }
        if(task.getPriority() == null) {
            throw new IllegalArgumentException("Task priority is required");
        }
        if(task.getStatus() == null) {
            throw new IllegalArgumentException("Task status is required");
        }

        Task existingTask = getTask(taskListId, taskId).orElseThrow(() -> new IllegalArgumentException("Task does not exist"));
        LocalDateTime now = LocalDateTime.now();
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(now);
        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID id) {
        taskRepository.deleteByTaskListIdAndId(taskListId, id);
    }
}
