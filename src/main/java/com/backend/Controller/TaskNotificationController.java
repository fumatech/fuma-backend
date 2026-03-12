package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.TaskNotification;
import com.backend.Service.TaskNotificationService;

@RestController
@RequestMapping("/task-notification")
@CrossOrigin(origins = {"http://localhost:3000", "https://fusionmastertech.com",
    "https://www.fusionmastertech.com", "http://fusionmastertech.com",
    "http://www.fusionmastertech.com"}, allowCredentials = "true")
public class TaskNotificationController {

    @Autowired
    private TaskNotificationService notificationService;

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskNotification>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(notificationService.getNotificationsByEmployee(employeeId));
    }

    @GetMapping("/unread/{employeeId}")
    public ResponseEntity<List<TaskNotification>> getUnread(@PathVariable Long employeeId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(employeeId));
    }

    @GetMapping("/unread-count/{employeeId}")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long employeeId) {
        return ResponseEntity.ok(notificationService.getUnreadCount(employeeId));
    }

    @PutMapping("/mark-read/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }

    @PutMapping("/mark-all-read/{employeeId}")
    public ResponseEntity<String> markAllAsRead(@PathVariable Long employeeId) {
        notificationService.markAllAsRead(employeeId);
        return ResponseEntity.ok("All notifications marked as read");
    }
}
