package com.backend.ServiceImpl;

import com.backend.Entity.NoticeBoard;
import com.backend.Repository.NoticeBoardRepo;
import com.backend.Service.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepo noticeBoardRepo;

    @Autowired
    public NoticeBoardServiceImpl(NoticeBoardRepo noticeBoardRepo) {
        this.noticeBoardRepo = noticeBoardRepo;
    }

    @Override
    public NoticeBoard save(NoticeBoard notice) {
        return noticeBoardRepo.save(notice);
    }

    @Override
    public List<NoticeBoard> getAll() {
        return noticeBoardRepo.findAll();
    }

    @Override
    public NoticeBoard getById(Long id) {
        return noticeBoardRepo.findById(id).orElse(null);
    }

    @Override
    public List<NoticeBoard> getActiveNotices() {
        return noticeBoardRepo.findByActiveOrderByPinnedDescCreatedAtDesc(true);
    }

    @Override
    public List<NoticeBoard> getByCategory(String category) {
        return noticeBoardRepo.findByCategory(category);
    }

    @Override
    public List<NoticeBoard> getByPostedBy(Long postedBy) {
        return noticeBoardRepo.findByPostedBy(postedBy);
    }

    @Override
    public List<NoticeBoard> getActiveNoticesForEmployee(Long employeeId) {
        return noticeBoardRepo.findActiveNoticesForEmployee(employeeId);
    }

    @Override
    public NoticeBoard update(Long id, NoticeBoard updatedNotice) {
        Optional<NoticeBoard> optional = noticeBoardRepo.findById(id);
        if (optional.isPresent()) {
            NoticeBoard existing = optional.get();
            existing.setTitle(updatedNotice.getTitle());
            existing.setContent(updatedNotice.getContent());
            existing.setCategory(updatedNotice.getCategory());
            existing.setPriority(updatedNotice.getPriority());
            existing.setPostedBy(updatedNotice.getPostedBy());
            existing.setTargetEmployee(updatedNotice.getTargetEmployee());
            existing.setPinned(updatedNotice.isPinned());
            existing.setActive(updatedNotice.isActive());
            existing.setExpiresAt(updatedNotice.getExpiresAt());
            return noticeBoardRepo.save(existing);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<NoticeBoard> optional = noticeBoardRepo.findById(id);
        if (optional.isPresent()) {
            noticeBoardRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
