package com.backend.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.backend.Entity.Task;
import com.backend.Entity.TaskNotification;
import com.backend.Entity.TaskNotification.NotificationType;
import com.backend.Repository.TaskNotificationRepository;
import com.backend.Repository.TaskRepository;
import com.backend.Service.TaskNotificationService;

@Service
public class TaskNotificationServiceImpl implements TaskNotificationService {

    @Autowired
    private TaskNotificationRepository notificationRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Override
    public TaskNotification createNotification(Long employeeId, Long taskId, String message,
            NotificationType type) {
        TaskNotification n = new TaskNotification();
        n.setEmployeeId(employeeId);
        n.setTaskId(taskId);
        n.setMessage(message);
        n.setType(type);
        n.setReadStatus(false);
        n.setCreatedAt(LocalDateTime.now());
        return notificationRepo.save(n);
    }

    @Override
    public List<TaskNotification> getNotificationsByEmployee(Long employeeId) {
        return notificationRepo.findByEmployeeIdOrderByCreatedAtDesc(employeeId);
    }

    @Override
    public List<TaskNotification> getUnreadNotifications(Long employeeId) {
        return notificationRepo.findUnreadByEmployeeId(employeeId);
    }

    @Override
    public long getUnreadCount(Long employeeId) {
        return notificationRepo.countUnreadByEmployeeId(employeeId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        notificationRepo.findById(notificationId).ifPresent(n -> {
            n.setReadStatus(true);
            notificationRepo.save(n);
        });
    }

    @Override
    public void markAllAsRead(Long employeeId) {
        List<TaskNotification> unread = notificationRepo
                .findUnreadByEmployeeId(employeeId);
        for (TaskNotification n : unread) {
            n.setReadStatus(true);
        }
        notificationRepo.saveAll(unread);
    }

    @Override
    @Scheduled(fixedRate = 1800000) // every 30 minutes
    public void generateReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threshold = now.plusHours(2);

        List<Task> tasks = taskRepo.findOverdueOrNearDeadline(threshold);

        for (Task task : tasks) {
            boolean isOverdue = task.getDeadline().isBefore(now);
            NotificationType type = isOverdue ? NotificationType.TASK_OVERDUE
                    : NotificationType.TASK_DUE_SOON;

            // Only create if no unread notification of this type already exists
            boolean exists = notificationRepo.existsByTaskIdAndTypeUnread(task.getId(), type);
            if (!exists) {
                String msg = isOverdue
                        ? "Task \"" + task.getTitle() + "\" is overdue!"
                        : "Task \"" + task.getTitle() + "\" is due soon (deadline: "
                        + task.getDeadline().toString() + ")";
                createNotification(task.getAssignedTo(), task.getId(), msg, type);
            }
        }
    }
}
