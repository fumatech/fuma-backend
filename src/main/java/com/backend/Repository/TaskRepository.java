package com.backend.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Task;
import com.backend.Entity.Task.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo(Long employeeId);

    List<Task> findByAssignedBy(Long managerId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedToAndStatus(Long employeeId, TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.status <> 'COMPLETED' AND t.deadline <= :threshold")
    List<Task> findOverdueOrNearDeadline(LocalDateTime threshold);

    @Query("SELECT t FROM Task t WHERE t.assignedTo = :employeeId AND t.status <> 'COMPLETED' AND t.deadline <= :threshold")
    List<Task> findOverdueOrNearDeadlineByEmployee(Long employeeId, LocalDateTime threshold);
}
