package com.backend.Service;

import java.util.List;

import com.backend.Entity.Task;

public interface TaskService {

    Task createTask(Task task);

    Task updateTask(Long id, Task task);

    Task updateStatus(Long id, Task.TaskStatus status);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    List<Task> getTasksByEmployee(Long employeeId);

    List<Task> getTasksByAssigner(Long managerId);

    List<Task> getOverdueOrNearDeadlineTasks();

    List<Task> getOverdueOrNearDeadlineTasksByEmployee(Long employeeId);

    void deleteTask(Long id);
}
