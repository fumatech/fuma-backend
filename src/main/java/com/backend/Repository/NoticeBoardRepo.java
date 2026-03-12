package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.NoticeBoard;

@Repository
public interface NoticeBoardRepo extends JpaRepository<NoticeBoard, Long> {

    List<NoticeBoard> findByActive(boolean active);

    List<NoticeBoard> findByCategory(String category);

    List<NoticeBoard> findByPriority(String priority);

    List<NoticeBoard> findByPinned(boolean pinned);

    List<NoticeBoard> findByPostedBy(Long postedBy);

    List<NoticeBoard> findByActiveOrderByPinnedDescCreatedAtDesc(boolean active);

    @Query("SELECT n FROM NoticeBoard n WHERE n.active = true AND (n.targetEmployee IS NULL OR n.targetEmployee = :employeeId) ORDER BY n.pinned DESC, n.createdAt DESC")
    List<NoticeBoard> findActiveNoticesForEmployee(@Param("employeeId") Long employeeId);
}
