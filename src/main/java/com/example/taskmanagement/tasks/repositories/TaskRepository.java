package com.example.taskmanagement.tasks.repositories;

import com.example.taskmanagement.tasks.entities.Task;
import com.example.taskmanagement.tasks.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    //@Query("SELECT COUNT(t) FROM Task t WHERE t.status = :taskStatus")
    long countByStatus(TaskStatus taskStatus);

    //@Query("SELECT COUNT(t) FROM Task t WHERE t.status = :taskStatus AND t.user.id = :id")
    long countByStatusAndUserId(TaskStatus taskStatus, int id);

    //@Query("SELECT t FROM Task t WHERE t.dueDate < :dateTime")
    List<Task> findAllByDueDateIsBefore(LocalDateTime dateTime);

    //@Query("SELECT t FROM Task t WHERE t.dueDate < :dateTime AND t.user.id = :id")
    List<Task> findAllByDueDateIsBeforeAndUserId(LocalDateTime dateTime, int id);

    //@Query("SELECT t FROM Task t WHERE t.dueDate > :dateTime")
    List<Task> findAllByDueDateIsAfter(LocalDateTime dateTime);

    //@Query("SELECT t FROM Task t WHERE t.dueDate > :dateTime AND t.user.id = :id")
    List<Task> findAllByDueDateIsAfterAndUserId(LocalDateTime dateTime, int id);

}
