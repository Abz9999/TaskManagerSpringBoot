package com.abz.task_manager.services;

import com.abz.task_manager.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    TaskList updateTaskList(UUID taskListId,  TaskList taskList);
    Optional<TaskList> findTaskListById(UUID id);
    void deleteTaskListById(UUID id);




}
