package com.example.taskmanagement.tasks.services;

import com.example.taskmanagement.tasks.dtos.CreateTaskRequest;
import com.example.taskmanagement.tasks.dtos.FilterTasksByDateAsAdminRequest;
import com.example.taskmanagement.tasks.dtos.FilterTasksByDateRequest;
import com.example.taskmanagement.tasks.dtos.GetTaskResponse;
import com.example.taskmanagement.tasks.dtos.ListTaskItem;
import com.example.taskmanagement.tasks.dtos.TaskByStatusRequest;
import com.example.taskmanagement.tasks.dtos.UpdateTaskRequest;
import com.example.taskmanagement.tasks.enums.TaskStatus;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    GetTaskResponse getTask(@NonNull UUID taskId);

    UUID createTask(@NonNull CreateTaskRequest request);

    void updateTask(@NonNull UUID taskId, @NonNull UpdateTaskRequest request);

    void deleteTask(@NonNull UUID taskId);

    List<ListTaskItem> listTask();

    List<ListTaskItem> listTaskAsAdmin();

    long countByStatusAsAdmin(TaskByStatusRequest taskByStatusRequest);

    long countByStatus(TaskStatus taskStatus);

    List<ListTaskItem> listTaskBeforeAsAdmin(FilterTasksByDateAsAdminRequest filterTasksByDateAsAdminRequest);

    List<ListTaskItem> listTaskAfterAsAdmin(FilterTasksByDateAsAdminRequest filterTasksByDateAsAdminRequest);

    List<ListTaskItem> listTaskBefore(FilterTasksByDateRequest filterTasksByDateRequest);

    List<ListTaskItem> listTaskAfter(FilterTasksByDateRequest filterTasksByDateRequest);
}
