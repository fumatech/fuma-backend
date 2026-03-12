package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.Task;
import com.backend.Entity.Task.TaskStatus;
import com.backend.Entity.TaskNotification;
import com.backend.Service.TaskNotificationService;
import com.backend.Service.TaskService;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskNotificationService notificationService;

    @PostMapping("/add")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task saved = taskService.createTask(task);
        notificationService.createNotification(
                saved.getAssignedTo(), saved.getId(),
                "New task assigned: \"" + saved.getTitle() + "\"",
                TaskNotification.NotificationType.TASK_ASSIGNED);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        TaskStatus status = TaskStatus.valueOf(body.get("status"));
        String report = body.get("completionReport");
        Task task = taskService.updateStatus(id, status);
        if (report != null && !report.trim().isEmpty()) {
            task.setCompletionReport(report);
            task = taskService.updateTask(task.getId(), task);
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Task>> getTasksByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.getTasksByEmployee(employeeId));
    }

    @GetMapping("/assigned-by/{managerId}")
    public ResponseEntity<List<Task>> getTasksByAssigner(@PathVariable Long managerId) {
        return ResponseEntity.ok(taskService.getTasksByAssigner(managerId));
    }

    @GetMapping("/reminders")
    public ResponseEntity<List<Task>> getReminders() {
        return ResponseEntity.ok(taskService.getOverdueOrNearDeadlineTasks());
    }

    @GetMapping("/reminders/{employeeId}")
    public ResponseEntity<List<Task>> getEmployeeReminders(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.getOverdueOrNearDeadlineTasksByEmployee(employeeId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
