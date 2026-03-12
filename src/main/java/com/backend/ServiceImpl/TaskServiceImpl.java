package com.backend.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Task;
import com.backend.Entity.Task.TaskStatus;
import com.backend.Repository.TaskRepository;
import com.backend.Service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setAssignedTo(updatedTask.getAssignedTo());
        existing.setStartTime(updatedTask.getStartTime());
        existing.setDeadline(updatedTask.getDeadline());
        existing.setPriority(updatedTask.getPriority());
        existing.setStatus(updatedTask.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existing);
    }

    @Override
    public Task updateStatus(Long id, TaskStatus status) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        existing.setStatus(status);
        existing.setUpdatedAt(LocalDateTime.now());
        if (status == TaskStatus.COMPLETED) {
            existing.setCompletedAt(LocalDateTime.now());
        }
        return taskRepository.save(existing);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public List<Task> getTasksByEmployee(Long employeeId) {
        return taskRepository.findByAssignedTo(employeeId);
    }

    @Override
    public List<Task> getTasksByAssigner(Long managerId) {
        return taskRepository.findByAssignedBy(managerId);
    }

    @Override
    public List<Task> getOverdueOrNearDeadlineTasks() {
        // Tasks due within next 2 hours or already overdue
        LocalDateTime threshold = LocalDateTime.now().plusHours(2);
        return taskRepository.findOverdueOrNearDeadline(threshold);
    }

    @Override
    public List<Task> getOverdueOrNearDeadlineTasksByEmployee(Long employeeId) {
        LocalDateTime threshold = LocalDateTime.now().plusHours(2);
        return taskRepository.findOverdueOrNearDeadlineByEmployee(employeeId, threshold);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
