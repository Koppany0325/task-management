package com.example.taskmanagement.tasks.controllers;

import com.example.taskmanagement.tasks.dtos.CreateTaskRequest;
import com.example.taskmanagement.tasks.dtos.FilterTasksByDateAsAdminRequest;
import com.example.taskmanagement.tasks.dtos.FilterTasksByDateRequest;
import com.example.taskmanagement.tasks.dtos.GetTaskResponse;
import com.example.taskmanagement.tasks.dtos.ListTaskItem;
import com.example.taskmanagement.tasks.dtos.TaskByStatusRequest;
import com.example.taskmanagement.tasks.dtos.UpdateTaskRequest;
import com.example.taskmanagement.tasks.enums.TaskStatus;
import com.example.taskmanagement.tasks.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public GetTaskResponse getTask(@PathVariable UUID id) {
        return taskService.getTask(id);
    }

    @PostMapping
    public UUID createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable UUID id, @Valid @RequestBody UpdateTaskRequest request) {
        taskService.updateTask(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    public List<ListTaskItem> listTask() {
        return taskService.listTask();
    }

    @GetMapping("/admin")
    public List<ListTaskItem> listTaskAsAdmin() {
        return taskService.listTaskAsAdmin();
    }

    @PostMapping("/admin/report/countByStatus")
    public long countTasksByStatusAsAdmin(@Valid @RequestBody TaskByStatusRequest taskByStatusRequest) {
        return taskService.countByStatusAsAdmin(taskByStatusRequest);
    }

    @PostMapping("/report/countByStatus")
    public long countTasksByStatus(@NotNull TaskStatus taskStatus) {
        return taskService.countByStatus(taskStatus);
    }

    @PostMapping("/report/tasksBeforeDate")
    public List<ListTaskItem> listTasksBefore(@RequestBody @Valid FilterTasksByDateRequest dateRequest) {
        return taskService.listTaskBefore(dateRequest);
    }

    @PostMapping("/report/tasksAfterDate")
    public List<ListTaskItem> listTasksAfter(@RequestBody @Valid FilterTasksByDateRequest dateRequest) {
        return taskService.listTaskAfter(dateRequest);
    }

    @PostMapping("/admin/report/tasksBeforeDate")
    public List<ListTaskItem> listTasksBeforeAsAdmin(@RequestBody @Valid FilterTasksByDateAsAdminRequest dateRequest) {
        return taskService.listTaskBeforeAsAdmin(dateRequest);
    }

    @PostMapping("/admin/report/tasksAfterDate")
    public List<ListTaskItem> listTasksAfterAsAdmin(@RequestBody @Valid FilterTasksByDateAsAdminRequest dateRequest) {
        return taskService.listTaskAfterAsAdmin(dateRequest);
    }
}
