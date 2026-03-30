package com.backend.Service;

import java.util.List;
import com.backend.Entity.Tag;

public interface TagService {
    Tag saveTag(Tag tag);
    List<Tag> getAllTags();
    Tag getTagById(Long id);
    Tag updateTag(Long id, Tag tag);
    void deleteTag(Long id);
    Tag findByName(String name);
}
