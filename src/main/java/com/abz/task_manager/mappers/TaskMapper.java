package com.abz.task_manager.mappers;

import com.abz.task_manager.domain.dto.TaskDto;
import com.abz.task_manager.domain.entities.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task fromDto(TaskDto taskDto);


}
