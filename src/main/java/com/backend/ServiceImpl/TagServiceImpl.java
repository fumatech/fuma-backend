package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.Entity.Tag;
import com.backend.Repository.TagRepo;
import com.backend.Service.TagService;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepo tagRepo;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepo.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepo.findById(id).orElse(null);
    }

    @Override
    public Tag updateTag(Long id, Tag updatedTag) {
        Optional<Tag> existingTag = tagRepo.findById(id);
        if (existingTag.isPresent()) {
            Tag tag = existingTag.get();
            tag.setName(updatedTag.getName());
            return tagRepo.save(tag);
        }
        return null;
    }

    @Override
    public void deleteTag(Long id) {
        tagRepo.deleteById(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepo.findByName(name).orElse(null);
    }
}
