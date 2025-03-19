package com.ajax.bulletinboard.controller;

import com.ajax.bulletinboard.model.Bulletin;
import com.ajax.bulletinboard.service.BulletinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BulletinControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BulletinService bulletinService;

    @InjectMocks
    private BulletinController bulletinController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bulletinController).build();
    }

    @Test
    public void testGetAllBulletins() throws Exception {
        when(bulletinService.getAllBulletins()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/bulletins"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testCreateBulletin() throws Exception {
        Bulletin bulletin = new Bulletin();
        bulletin.setTitle("Test Bulletin");
        bulletin.setContent("Test Content");

        when(bulletinService.createBulletin(any(Bulletin.class))).thenReturn(new Bulletin());

        mockMvc.perform(post("/api/bulletins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bulletin)))
                .andExpect(status().isCreated());
    }
}
