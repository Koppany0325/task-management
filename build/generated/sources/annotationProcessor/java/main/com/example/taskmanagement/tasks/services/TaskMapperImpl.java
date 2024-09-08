package com.example.taskmanagement.tasks.services;

import com.example.taskmanagement.tasks.dtos.CreateTaskRequest;
import com.example.taskmanagement.tasks.dtos.GetTaskResponse;
import com.example.taskmanagement.tasks.dtos.ListTaskItem;
import com.example.taskmanagement.tasks.dtos.UpdateTaskRequest;
import com.example.taskmanagement.tasks.entities.Task;
import com.example.taskmanagement.tasks.enums.TaskStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-07T15:10:55+0200",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task createTaskRequestToTask(CreateTaskRequest request) {
        if ( request == null ) {
            return null;
        }

        Task task = new Task();

        task.setTitle( request.getTitle() );
        task.setDescription( request.getDescription() );
        task.setDueDate( request.getDueDate() );
        task.setStatus( request.getStatus() );

        return task;
    }

    @Override
    public void updateTaskRequestToTask(Task task, UpdateTaskRequest request) {
        if ( request == null ) {
            return;
        }

        task.setTitle( request.title() );
        task.setDescription( request.description() );
        task.setDueDate( request.dueDate() );
        task.setStatus( request.status() );
    }

    @Override
    public GetTaskResponse taskToGetTaskResponse(Task task) {
        if ( task == null ) {
            return null;
        }

        UUID id = null;
        String title = null;
        String description = null;
        LocalDateTime dueDate = null;
        TaskStatus status = null;

        id = task.getId();
        title = task.getTitle();
        description = task.getDescription();
        dueDate = task.getDueDate();
        status = task.getStatus();

        GetTaskResponse getTaskResponse = new GetTaskResponse( id, title, description, dueDate, status );

        return getTaskResponse;
    }

    @Override
    public List<ListTaskItem> taskListToTaskItemList(List<Task> taskList) {
        if ( taskList == null ) {
            return null;
        }

        List<ListTaskItem> list = new ArrayList<ListTaskItem>( taskList.size() );
        for ( Task task : taskList ) {
            list.add( taskToListTaskitem( task ) );
        }

        return list;
    }

    @Override
    public ListTaskItem taskToListTaskitem(Task task) {
        if ( task == null ) {
            return null;
        }

        UUID id = null;
        String title = null;
        String description = null;
        LocalDateTime dueDate = null;
        TaskStatus status = null;

        id = task.getId();
        title = task.getTitle();
        description = task.getDescription();
        dueDate = task.getDueDate();
        status = task.getStatus();

        ListTaskItem listTaskItem = new ListTaskItem( id, title, description, dueDate, status );

        return listTaskItem;
    }
}
