package com.backend.Service;

import java.util.List;

import com.backend.Entity.TaskNotification;

public interface TaskNotificationService {

    TaskNotification createNotification(Long employeeId, Long taskId, String message,
            TaskNotification.NotificationType type);

    List<TaskNotification> getNotificationsByEmployee(Long employeeId);

    List<TaskNotification> getUnreadNotifications(Long employeeId);

    long getUnreadCount(Long employeeId);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long employeeId);

    void generateReminders();
}
