package com.example.taskmanagement.tasks.services;

import com.example.taskmanagement.tasks.dtos.CreateTaskRequest;
import com.example.taskmanagement.tasks.dtos.FilterTasksByDateAsAdminRequest;
import com.example.taskmanagement.tasks.dtos.FilterTasksByDateRequest;
import com.example.taskmanagement.tasks.dtos.GetTaskResponse;
import com.example.taskmanagement.tasks.dtos.ListTaskItem;
import com.example.taskmanagement.tasks.dtos.TaskByStatusRequest;
import com.example.taskmanagement.tasks.dtos.UpdateTaskRequest;
import com.example.taskmanagement.tasks.entities.Task;
import com.example.taskmanagement.tasks.enums.TaskStatus;
import com.example.taskmanagement.tasks.repositories.TaskRepository;
import com.example.taskmanagement.users.entities.Role;
import com.example.taskmanagement.users.entities.User;
import com.example.taskmanagement.users.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskMapper taskMapper;

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public GetTaskResponse getTask(@NonNull UUID taskId) {
        Task task = findTaskAndCheckForAuthorization(taskId);
        GetTaskResponse taskResponse = taskMapper.taskToGetTaskResponse(task);
        return taskResponse;
    }

    @Override
    public UUID createTask(@NonNull CreateTaskRequest request) {
        Task task = taskMapper.createTaskRequestToTask(request);
        task.setUser(getUser());
        return taskRepository.save(task).getId();
    }

    @Override
    public void updateTask(@NonNull UUID taskId, @NonNull UpdateTaskRequest request) {
        Task task = findTaskAndCheckForAuthorization(taskId);
        taskMapper.updateTaskRequestToTask(task,request);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(@NonNull UUID taskId) {
        Task task = findTaskAndCheckForAuthorization(taskId);
        taskRepository.delete(task);
    }

    @Override
    public List<ListTaskItem> listTask() {
    return taskMapper.taskListToTaskItemList(getUser().getTasks());
    }

    @Override
    public List<ListTaskItem> listTaskAsAdmin() {
        checkIfUserIsAdmin();
        return taskMapper.taskListToTaskItemList(taskRepository.findAll());
    }

    @Override
    public long countByStatusAsAdmin(TaskByStatusRequest taskByStatusRequest) {
        checkIfUserIsAdmin();
        if(taskByStatusRequest.id() != null) {
            return taskRepository.countByStatusAndUserId(taskByStatusRequest.status(), taskByStatusRequest.id());
        }
        return taskRepository.countByStatus(taskByStatusRequest.status());
    }

    @Override
    public long countByStatus(TaskStatus status) {
        return taskRepository.countByStatusAndUserId(status, getUser().getId());
    }

    @Override
    public List<ListTaskItem> listTaskBeforeAsAdmin(FilterTasksByDateAsAdminRequest filterTasksByDateAsAdminRequest) {
        checkIfUserIsAdmin();
        if(filterTasksByDateAsAdminRequest.id() != null) {
            return taskMapper.taskListToTaskItemList(taskRepository.findAllByDueDateIsBeforeAndUserId(filterTasksByDateAsAdminRequest.date(), filterTasksByDateAsAdminRequest.id()));
        }
        return taskMapper.taskListToTaskItemList(taskRepository.findAllByDueDateIsBefore(filterTasksByDateAsAdminRequest.date()));
    }

    @Override
    public List<ListTaskItem> listTaskAfterAsAdmin(FilterTasksByDateAsAdminRequest filterTasksByDateAsAdminRequest) {
        checkIfUserIsAdmin();
        if(filterTasksByDateAsAdminRequest.id() != null) {
            return taskMapper.taskListToTaskItemList(taskRepository.findAllByDueDateIsAfterAndUserId(filterTasksByDateAsAdminRequest.date(), filterTasksByDateAsAdminRequest.id()));
        }
        return taskMapper.taskListToTaskItemList(taskRepository.findAllByDueDateIsAfter(filterTasksByDateAsAdminRequest.date()));
    }

    @Override
    public List<ListTaskItem> listTaskBefore(FilterTasksByDateRequest filterTasksByDateRequest) {
        return taskMapper.taskListToTaskItemList(taskRepository.findAllByDueDateIsBeforeAndUserId(filterTasksByDateRequest.date(),getUser().getId()));
    }

    @Override
    public List<ListTaskItem> listTaskAfter(FilterTasksByDateRequest filterTasksByDateRequest) {
        return taskMapper.taskListToTaskItemList(taskRepository.findAllByDueDateIsAfterAndUserId(filterTasksByDateRequest.date(),getUser().getId()));
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    private void checkIfUserCorrectOrAdmin(User user) {
        User logedInAs = getUser();
        if(user.getId() != logedInAs.getId() && !logedInAs.getRoles().stream().map(Role::getName).toList().contains("ADMIN")) {
            throw new RuntimeException("Wrong user");
        }
    }

    private void checkIfUserIsAdmin() {
        if(!getUser().getRoles().stream().map(Role::getName).toList().contains("ADMIN")) {
            throw new RuntimeException("You need admin role to list all tasks");
        }
    }

    private Task findTaskAndCheckForAuthorization(UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        checkIfUserCorrectOrAdmin(task.getUser());
        return task;
    }

}
