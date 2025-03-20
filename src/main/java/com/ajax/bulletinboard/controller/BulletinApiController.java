package com.ajax.bulletinboard.controller;

import com.ajax.bulletinboard.model.Bulletin;
import com.ajax.bulletinboard.service.BulletinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bulletins")
public class BulletinApiController {
    private final BulletinService bulletinService;

    public BulletinApiController(BulletinService bulletinService) {
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
    public ResponseEntity<Void> updateBulletin(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        String password = (String) request.get("password");
        Bulletin bulletin = new Bulletin();
        bulletin.setTitle((String) request.get("title"));
        bulletin.setContent((String) request.get("content"));

        bulletinService.updateBulletin(id, bulletin, password);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBulletin(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String password = request.get("password");
        bulletinService.deleteBulletin(id, password);
        return ResponseEntity.noContent().build();
    }
}
