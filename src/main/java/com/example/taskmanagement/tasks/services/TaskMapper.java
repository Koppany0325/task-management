package com.example.taskmanagement.tasks.services;

import com.example.taskmanagement.tasks.dtos.CreateTaskRequest;
import com.example.taskmanagement.tasks.dtos.GetTaskResponse;
import com.example.taskmanagement.tasks.dtos.ListTaskItem;
import com.example.taskmanagement.tasks.dtos.UpdateTaskRequest;
import com.example.taskmanagement.tasks.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    Task createTaskRequestToTask(CreateTaskRequest request);

    void updateTaskRequestToTask(@MappingTarget Task task, UpdateTaskRequest request);

    GetTaskResponse taskToGetTaskResponse(Task task);

    List<ListTaskItem> taskListToTaskItemList(List<Task> taskList);

    ListTaskItem taskToListTaskitem(Task task);

}
