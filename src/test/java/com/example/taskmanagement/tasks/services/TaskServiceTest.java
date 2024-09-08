package com.example.taskmanagement.tasks.services;

import com.example.taskmanagement.tasks.dtos.CreateTaskRequest;
import com.example.taskmanagement.tasks.dtos.FilterTasksByDateRequest;
import com.example.taskmanagement.tasks.dtos.UpdateTaskRequest;
import com.example.taskmanagement.tasks.entities.Task;
import com.example.taskmanagement.tasks.enums.TaskStatus;
import com.example.taskmanagement.tasks.repositories.TaskRepository;
import com.example.taskmanagement.users.entities.User;
import com.example.taskmanagement.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Spy
    private TaskMapper taskMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;


    private User user;
    private Task task;
    private UUID taskId;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        user.setUsername("testuser");

        taskId = UUID.randomUUID();
        task = new Task();
        task.setId(taskId);
        task.setUser(user);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
    }

    @Test
    void getTask() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.getTask(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskMapper, times(1)).taskToGetTaskResponse(task);
    }

    @Test
    void createTask () {
        CreateTaskRequest request = new CreateTaskRequest();
        Task newTask = new Task();
        newTask.setId(taskId);

        when(taskMapper.createTaskRequestToTask(request)).thenReturn(newTask);
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        UUID result = taskService.createTask(request);

        assertEquals(taskId, result);
        verify(taskRepository, times(1)).save(newTask);
    }

    @Test
    void updateTask() {
        UpdateTaskRequest updateRequest = new UpdateTaskRequest("test","test", LocalDateTime.now(), TaskStatus.COMPLETED);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.updateTask(taskId, updateRequest);

        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).updateTaskRequestToTask(task, updateRequest);
    }

    @Test
    void deleteTask() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void countByStatus() {
        when(taskRepository.countByStatusAndUserId(TaskStatus.COMPLETED, user.getId())).thenReturn(5L);

        long count = taskService.countByStatus(TaskStatus.COMPLETED);

        assertEquals(5, count);
        verify(taskRepository, times(1)).countByStatusAndUserId(TaskStatus.COMPLETED, user.getId());
    }

    @Test
    void listTasks() {
        when(taskMapper.taskListToTaskItemList(user.getTasks())).thenReturn(List.of());

        taskService.listTask();

        verify(taskMapper, times(1)).taskListToTaskItemList(user.getTasks());
    }

    @Test
    void listTasksBefore() {
        FilterTasksByDateRequest filterRequest = new FilterTasksByDateRequest(LocalDateTime.now());

        when(taskRepository.findAllByDueDateIsBeforeAndUserId(any(), eq(user.getId()))).thenReturn(List.of());
        when(taskMapper.taskListToTaskItemList(List.of())).thenReturn(List.of());

        taskService.listTaskBefore(filterRequest);

        verify(taskRepository, times(1)).findAllByDueDateIsBeforeAndUserId(any(), eq(user.getId()));
        verify(taskMapper, times(1)).taskListToTaskItemList(List.of());
    }
}
