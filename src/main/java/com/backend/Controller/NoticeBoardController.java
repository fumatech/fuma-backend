package com.backend.Controller;

import java.util.List;

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

import com.backend.Entity.NoticeBoard;
import com.backend.Entity.User;
import com.backend.Entity.TaskNotification;
import com.backend.Service.NoticeBoardService;
import com.backend.Service.TaskNotificationService;
import com.backend.Repository.UserRepo;

@RestController
@RequestMapping("/notice-board")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://fusionmastertech.com",
    "https://fusionmastertech.com",
    "http://www.fusionmastertech.com",
    "https://www.fusionmastertech.com"
}, allowCredentials = "true")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final TaskNotificationService taskNotificationService;
    private final UserRepo userRepo;

    @Autowired
    public NoticeBoardController(NoticeBoardService noticeBoardService,
            TaskNotificationService taskNotificationService,
            UserRepo userRepo) {
        this.noticeBoardService = noticeBoardService;
        this.taskNotificationService = taskNotificationService;
        this.userRepo = userRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<NoticeBoard> addNotice(@RequestBody NoticeBoard notice) {
        NoticeBoard created = noticeBoardService.save(notice);

        // Notify employees about the new notice
        try {
            String prefix = "";
            if ("Alert".equals(created.getCategory())) {
                prefix = "\u26a0\ufe0f ALERT: ";
            } else if ("Achievement".equals(created.getCategory())) {
                prefix = "\ud83c\udfc6 ";
            } else {
                prefix = "\ud83d\udccc ";
            }
            String message = prefix + created.getTitle();

            if (created.getTargetEmployee() != null) {
                // Send to specific employee only
                taskNotificationService.createNotification(
                        created.getTargetEmployee(),
                        null,
                        message,
                        TaskNotification.NotificationType.NOTICE_POSTED
                );
            } else {
                // Send to ALL active employees
                List<User> allUsers = userRepo.findAll();
                for (User user : allUsers) {
                    if (user.getIsActive() != null && user.getIsActive()) {
                        taskNotificationService.createNotification(
                                user.getId(),
                                null,
                                message,
                                TaskNotification.NotificationType.NOTICE_POSTED
                        );
                    }
                }
            }
        } catch (Exception e) {
            // Log but don't fail the notice creation if notifications fail
            System.err.println("Failed to send notice notifications: " + e.getMessage());
        }

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<NoticeBoard>> getAllNotices() {
        List<NoticeBoard> notices = noticeBoardService.getAll();
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<NoticeBoard> getNoticeById(@PathVariable("id") Long id) {
        NoticeBoard notice = noticeBoardService.getById(id);
        if (notice == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<NoticeBoard>> getActiveNotices() {
        List<NoticeBoard> notices = noticeBoardService.getActiveNotices();
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<NoticeBoard>> getByCategory(@PathVariable("category") String category) {
        List<NoticeBoard> notices = noticeBoardService.getByCategory(category);
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @GetMapping("/posted-by/{postedBy}")
    public ResponseEntity<List<NoticeBoard>> getByPostedBy(@PathVariable("postedBy") Long postedBy) {
        List<NoticeBoard> notices = noticeBoardService.getByPostedBy(postedBy);
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @GetMapping("/for-employee/{employeeId}")
    public ResponseEntity<List<NoticeBoard>> getNoticesForEmployee(@PathVariable("employeeId") Long employeeId) {
        List<NoticeBoard> notices = noticeBoardService.getActiveNoticesForEmployee(employeeId);
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NoticeBoard> updateNotice(@PathVariable("id") Long id, @RequestBody NoticeBoard updatedNotice) {
        NoticeBoard notice = noticeBoardService.update(id, updatedNotice);
        if (notice == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable("id") Long id) {
        boolean isDeleted = noticeBoardService.deleteById(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
