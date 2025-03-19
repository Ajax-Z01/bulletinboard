package com.ajax.bulletinboard.controller;

import com.ajax.bulletinboard.model.Bulletin;
import com.ajax.bulletinboard.service.BulletinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulletins")
public class BulletinController {
    private final BulletinService bulletinService;

    public BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Bulletin> getAllBulletins() {
        return bulletinService.getAllBulletins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bulletin> getBulletinById(@PathVariable Long id) {
        return bulletinService.getBulletinById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bulletin> createBulletin(@RequestBody Bulletin bulletin) {
        Bulletin createdBulletin = bulletinService.createBulletin(bulletin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBulletin);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBulletin(@PathVariable Long id, @RequestBody Bulletin bulletin) {
        bulletin.setId(id);
        bulletinService.updateBulletin(bulletin);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBulletin(@PathVariable Long id) {
        bulletinService.deleteBulletin(id);
        return ResponseEntity.noContent().build();
    }
}
