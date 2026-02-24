package com.abz.task_manager.services;

import com.abz.task_manager.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    public List<Task> listTasks(UUID taskListId);
    public Task createTask(Task  task, UUID taskListId);
    public Optional<Task> getTask(UUID taskListId, UUID id);
    public Task updateTask(UUID taskListId, UUID id, Task  task);
    public void deleteTask(UUID taskListId, UUID id);
}
