package com.backend.Service;

import com.backend.Entity.NoticeBoard;

import java.util.List;

public interface NoticeBoardService {

    NoticeBoard save(NoticeBoard notice);

    List<NoticeBoard> getAll();

    NoticeBoard getById(Long id);

    List<NoticeBoard> getActiveNotices();

    List<NoticeBoard> getByCategory(String category);

    List<NoticeBoard> getByPostedBy(Long postedBy);

    List<NoticeBoard> getActiveNoticesForEmployee(Long employeeId);

    NoticeBoard update(Long id, NoticeBoard updatedNotice);

    boolean deleteById(Long id);
}
