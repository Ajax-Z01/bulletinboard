package com.ajax.bulletinboard.service;

import com.ajax.bulletinboard.model.Bulletin;
import com.ajax.bulletinboard.repository.BulletinMapper;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BulletinService {
    private final BulletinMapper bulletinMapper;

    public BulletinService(BulletinMapper bulletinMapper) {
        this.bulletinMapper = bulletinMapper;
    }

    public List<Bulletin> getAllBulletins() {
        return bulletinMapper.findAll();
    }

    public Optional<Bulletin> getBulletinById(Long id) {
        Bulletin bulletin = bulletinMapper.findById(id);
        if (bulletin == null) {
            return Optional.empty();
        }
        bulletinMapper.incrementViews(id); 
        return Optional.of(bulletin);
    }    

    public Bulletin createBulletin(Bulletin bulletin) {
        if (bulletin.getTitle() == null || bulletin.getTitle().isEmpty() ||
            bulletin.getContent() == null || bulletin.getContent().isEmpty() ||
            bulletin.getAuthor() == null || bulletin.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Title, content, and author are required.");
        }
        bulletinMapper.insert(bulletin);
        return bulletin;
    }    

    public void updateBulletin(Long id, Bulletin bulletin, String password) {
        Bulletin existingBulletin = bulletinMapper.findById(id);
        if (existingBulletin == null) {
            throw new IllegalArgumentException("Bulletin not found.");
        }
        if (!existingBulletin.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password.");
        }
        if (bulletin.getTitle() != null) {
            existingBulletin.setTitle(bulletin.getTitle());
        }
        if (bulletin.getContent() != null) {
            existingBulletin.setContent(bulletin.getContent());
        }
        existingBulletin.setUpdatedAt(LocalDateTime.now());
        bulletinMapper.update(existingBulletin);
    }   

    public void deleteBulletin(Long id, String password) {
        Bulletin bulletin = bulletinMapper.findById(id);
        if (bulletin == null) {
            throw new IllegalArgumentException("Post not found.");
        }
        if (!bulletin.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password.");
        }
        bulletinMapper.softDelete(id);
    }    
    
    public void incrementViews(Long id) {
        bulletinMapper.incrementViews(id);
    }    
}
