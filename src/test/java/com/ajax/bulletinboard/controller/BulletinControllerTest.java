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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
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

    @Test
    public void testGetBulletinById() throws Exception {
        Bulletin bulletin = new Bulletin();
        bulletin.setId(1L);
        bulletin.setTitle("Test Bulletin");
        bulletin.setContent("Test Content");

        when(bulletinService.getBulletinById(1L)).thenReturn(Optional.of(bulletin));

        mockMvc.perform(get("/api/bulletins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Bulletin"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    public void testGetBulletinById_NotFound() throws Exception {
        when(bulletinService.getBulletinById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bulletins/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBulletin() throws Exception {
        Bulletin updatedBulletin = new Bulletin();
        updatedBulletin.setTitle("Updated Title");
        updatedBulletin.setContent("Updated Content");

        doNothing().when(bulletinService).updateBulletin(eq(1L), any(Bulletin.class));

        mockMvc.perform(put("/api/bulletins/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBulletin)))
                .andExpect(status().isNoContent());

        verify(bulletinService, times(1)).updateBulletin(eq(1L), any(Bulletin.class));
    }

    @Test
    public void testDeleteBulletin() throws Exception {
        doNothing().when(bulletinService).deleteBulletin(eq(1L), anyString());

        String requestBody = "{\"password\": \"testPassword\"}";

        mockMvc.perform(delete("/api/bulletins/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNoContent());

        verify(bulletinService, times(1)).deleteBulletin(eq(1L), anyString());
    }
}
