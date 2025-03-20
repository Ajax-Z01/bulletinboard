package com.ajax.bulletinboard.controller;

import com.ajax.bulletinboard.model.Bulletin;
import com.ajax.bulletinboard.service.BulletinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class BulletinController {
    private final BulletinService bulletinService;

    public BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    @GetMapping
    public String showBulletinList(Model model) {
        model.addAttribute("bulletins", bulletinService.getAllBulletins());
        return "index";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("bulletin", new Bulletin());
        return "create";
    }
    
    @PostMapping("/create")
    public String createBulletin(@ModelAttribute Bulletin bulletin, 
                                RedirectAttributes redirectAttributes) {
        bulletinService.createBulletin(bulletin);
        redirectAttributes.addFlashAttribute("successMessage", "Post created successfully!");
        return "redirect:/";
    }

    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        return bulletinService.getBulletinById(id)
                .map(bulletin -> {
                    model.addAttribute("bulletin", bulletin);
                    return "view";
                })
                .orElse("redirect:/");
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        return bulletinService.getBulletinById(id)
                .map(bulletin -> {
                    model.addAttribute("bulletin", bulletin);
                    return "edit";
                })
                .orElse("redirect:/");
    }
    
    @PostMapping("/edit")
    public String updateBulletin(@ModelAttribute Bulletin bulletin, 
                                @RequestParam String password, 
                                RedirectAttributes redirectAttributes) {
        try {
            bulletinService.updateBulletin(bulletin.getId(), bulletin, password);
            redirectAttributes.addFlashAttribute("successMessage", "Post updated successfully.");
            return "redirect:/view/" + bulletin.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/edit/" + bulletin.getId();
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteBulletin(@PathVariable Long id, 
                                @RequestParam String password, 
                                RedirectAttributes redirectAttributes) {
        try {
            bulletinService.deleteBulletin(id, password);
            redirectAttributes.addFlashAttribute("successMessage", "Post deleted successfully.");
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/view/" + id;
        }
    }
}
