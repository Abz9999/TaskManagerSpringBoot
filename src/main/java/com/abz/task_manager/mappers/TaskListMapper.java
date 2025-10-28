package com.abz.task_manager.mappers;

import com.abz.task_manager.domain.dto.TaskListDto;
import com.abz.task_manager.domain.entities.TaskList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface TaskListMapper {

    TaskListDto toDto(TaskList taskList);
    TaskList fromDto(TaskListDto taskListDto);
}
