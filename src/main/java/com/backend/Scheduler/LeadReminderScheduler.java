package com.backend.Scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.Entity.Lead;
import com.backend.Entity.TaskNotification.NotificationType;
import com.backend.Repository.LeadRepo;
import com.backend.Service.TaskNotificationService;

@Component
public class LeadReminderScheduler {

    @Autowired
    private LeadRepo leadRepo;

    @Autowired
    private TaskNotificationService notificationService;

    @Scheduled(fixedRate = 3600000) // Every 1 hour
    public void checkFollowUpReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime soon = now.plusHours(24);

        // Find leads with follow-up due in the next 24 hours that are still PENDING
        List<Lead> upcomingLeads = leadRepo.findFollowUpsInRange(now, soon, "PENDING");

        for (Lead lead : upcomingLeads) {
            String message = "Reminder: Follow-up due for lead \"" + lead.getName() + "\" at " + lead.getFollowUpDate().toString();
            // Using TASK_DUE_SOON as a generic type if no specific LEAD_REMINDER exists
            notificationService.createNotification(lead.getEmployeeid(), null, message, NotificationType.TASK_DUE_SOON);
        }

        // Find overdue leads
        List<Lead> overdueLeads = leadRepo.findOverdueFollowUps(now);
        for (Lead lead : overdueLeads) {
            String message = "Overdue: Follow-up was due for lead \"" + lead.getName() + "\" on " + lead.getFollowUpDate().toString();
            notificationService.createNotification(lead.getEmployeeid(), null, message, NotificationType.TASK_OVERDUE);
        }
    }
}
