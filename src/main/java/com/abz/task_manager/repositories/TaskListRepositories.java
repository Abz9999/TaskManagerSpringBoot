package com.abz.task_manager.repositories;

import com.abz.task_manager.domain.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskListRepositories extends JpaRepository<TaskList, UUID> {
}
