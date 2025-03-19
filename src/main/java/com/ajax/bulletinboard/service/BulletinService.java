package com.ajax.bulletinboard.service;

import com.ajax.bulletinboard.mapper.BulletinMapper;
import com.ajax.bulletinboard.model.Bulletin;
import org.springframework.stereotype.Service;
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
        return Optional.ofNullable(bulletinMapper.findById(id));
    }

    public Bulletin createBulletin(Bulletin bulletin) {
        bulletinMapper.insert(bulletin);
        return bulletin;
    }    

    public void updateBulletin(Bulletin bulletin) {
        bulletinMapper.update(bulletin);
    }

    public void deleteBulletin(Long id) {
        bulletinMapper.delete(id);
    }
}
