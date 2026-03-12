package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.TaskNotification;

@Repository
public interface TaskNotificationRepository extends JpaRepository<TaskNotification, Long> {

    List<TaskNotification> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);

    @Query("SELECT n FROM TaskNotification n WHERE n.employeeId = :employeeId AND n.readStatus = false ORDER BY n.createdAt DESC")
    List<TaskNotification> findUnreadByEmployeeId(Long employeeId);

    @Query("SELECT COUNT(n) FROM TaskNotification n WHERE n.employeeId = :employeeId AND n.readStatus = false")
    long countUnreadByEmployeeId(Long employeeId);

    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM TaskNotification n WHERE n.taskId = :taskId AND n.type = :type")
    boolean existsByTaskIdAndType(Long taskId, TaskNotification.NotificationType type);

    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM TaskNotification n WHERE n.taskId = :taskId AND n.type = :type AND n.readStatus = false")
    boolean existsByTaskIdAndTypeUnread(Long taskId, TaskNotification.NotificationType type);
}
