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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;


    @Test
    void getTask() {
        var id = UUID.randomUUID();
        GetTaskResponse getTaskResponse = new GetTaskResponse(id,"Teszt","Teszt", LocalDateTime.now(), TaskStatus.COMPLETED);
        Mockito.when(taskService.getTask(id)).thenReturn(getTaskResponse);

        var response = taskController.getTask(id);
        assertEquals(id,response.id());
    }

    @Test
    void createTask() {
        var id = UUID.randomUUID();
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        Mockito.when(taskService.createTask(createTaskRequest)).thenReturn(id);
        var response = taskController.createTask(createTaskRequest);
        assertEquals(id,response);
    }

    @Test
    void updateTask() {
        var id = UUID.randomUUID();
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest("Teszt","TesztDescription", LocalDateTime.now(), TaskStatus.COMPLETED);
        taskController.updateTask(id, updateTaskRequest);
        Mockito.verify(taskService,Mockito.times(1)).updateTask(id,updateTaskRequest);
    }

    @Test
    void deleteTask() {
        var id = UUID.randomUUID();
        taskController.deleteTask(id);
        Mockito.verify(taskService,Mockito.times(1)).deleteTask(id);
    }

    @Test
    void listTasks() {
        var id = UUID.randomUUID();
        ListTaskItem listTaskItem = new ListTaskItem(id,"Teszt","TesztDescription", LocalDateTime.now(), TaskStatus.COMPLETED);
        List<ListTaskItem> taskList = List.of(listTaskItem);
        Mockito.when(taskService.listTask()).thenReturn(taskList);
        var response = taskController.listTask();
        assertEquals(id, response.get(0).id());
    }

    @Test
    void listTasksAsAdmin() {
        var id = UUID.randomUUID();
        ListTaskItem listTaskItem = new ListTaskItem(id,"Teszt","TesztDescription", LocalDateTime.now(), TaskStatus.COMPLETED);
        List<ListTaskItem> taskList = List.of(listTaskItem);
        Mockito.when(taskService.listTaskAsAdmin()).thenReturn(taskList);
        var response = taskController.listTaskAsAdmin();
        assertEquals(id, response.get(0).id());
    }

    @Test
    void countTasksByStatusAsAdmin() {
        TaskByStatusRequest taskByStatusRequest = new TaskByStatusRequest(1,TaskStatus.COMPLETED);
        Mockito.when(taskService.countByStatusAsAdmin(taskByStatusRequest)).thenReturn(1L);
        var response = taskController.countTasksByStatusAsAdmin(taskByStatusRequest);
        assertEquals(1L,response);
    }

    @Test
    void countTasksByStatus() {
        Mockito.when(taskService.countByStatus(TaskStatus.COMPLETED)).thenReturn(1L);
        var response = taskController.countTasksByStatus(TaskStatus.COMPLETED);
        assertEquals(1L,response);
    }

    @Test
    void listTasksBefore() {
        var id = UUID.randomUUID();
        FilterTasksByDateRequest request = new FilterTasksByDateRequest(LocalDateTime.now());
        ListTaskItem item = new ListTaskItem(id,"Teszt","Teszt",LocalDateTime.now(),TaskStatus.COMPLETED);
        Mockito.when(taskService.listTaskBefore(request)).thenReturn(List.of(item));
        var response = taskController.listTasksBefore(request);
        assertEquals(id, response.get(0).id());
    }

    @Test
    void listTasksAfter() {
        var id = UUID.randomUUID();
        FilterTasksByDateRequest request = new FilterTasksByDateRequest(LocalDateTime.now());
        ListTaskItem item = new ListTaskItem(id,"Teszt","Teszt",LocalDateTime.now(),TaskStatus.COMPLETED);
        Mockito.when(taskService.listTaskAfter(request)).thenReturn(List.of(item));
        var response = taskController.listTasksAfter(request);
        assertEquals(id, response.get(0).id());
    }

    @Test
    void listTasksBeforeAsAdmin() {
        var id = UUID.randomUUID();
        FilterTasksByDateAsAdminRequest request = new FilterTasksByDateAsAdminRequest(LocalDateTime.now(), 1);
        ListTaskItem item = new ListTaskItem(id,"Teszt","Teszt",LocalDateTime.now(),TaskStatus.COMPLETED);
        Mockito.when(taskService.listTaskBeforeAsAdmin(request)).thenReturn(List.of(item));
        var response = taskController.listTasksBeforeAsAdmin(request);
        assertEquals(id, response.get(0).id());
    }

    @Test
    void listTasksAfterAsAdmin() {
        var id = UUID.randomUUID();
        FilterTasksByDateAsAdminRequest request = new FilterTasksByDateAsAdminRequest(LocalDateTime.now(), 1);
        ListTaskItem item = new ListTaskItem(id,"Teszt","Teszt",LocalDateTime.now(),TaskStatus.COMPLETED);
        Mockito.when(taskService.listTaskAfterAsAdmin(request)).thenReturn(List.of(item));
        var response = taskController.listTasksAfterAsAdmin(request);
        assertEquals(id, response.get(0).id());
    }



}
