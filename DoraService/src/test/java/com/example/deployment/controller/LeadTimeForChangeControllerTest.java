package com.example.deployment.controller;

import com.example.deployment.dto.LeadTimeForChangeDTO;
import com.example.deployment.service.LeadTimeForChangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LeadTimeForChangeControllerTest {

    @Mock
    private LeadTimeForChangeService service;

    @InjectMocks
    private LeadTimeForChangeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLeadTimes_WithValidParameters_ShouldReturnOkResponse() {
        // Arrange
        String team = "TeamA";
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";
        String interval = "week";

        LeadTimeForChangeDTO dto1 = new LeadTimeForChangeDTO(team, 
            LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-07"), interval, 24.0);
        LeadTimeForChangeDTO dto2 = new LeadTimeForChangeDTO(team, 
            LocalDate.parse("2024-01-08"), LocalDate.parse("2024-01-14"), interval, 36.0);

        when(service.getLeadTimes(eq(team), eq(startDate), eq(endDate), eq(interval)))
            .thenReturn(Arrays.asList(dto1, dto2));

        // Act
        ResponseEntity<List<LeadTimeForChangeDTO>> response = controller.getLeadTimes(team, startDate, endDate, interval);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(team, response.getBody().get(0).getTeam());
        assertEquals(interval, response.getBody().get(0).getInterval());
        verify(service).getLeadTimes(team, startDate, endDate, interval);
    }

    @Test
    void getLeadTimes_WithTeamOnly_ShouldReturnOkResponse() {
        // Arrange
        String team = "TeamA";
        LeadTimeForChangeDTO dto = new LeadTimeForChangeDTO(team, 
            LocalDate.now(), LocalDate.now(), "week", 24.0);

        when(service.getLeadTimes(eq(team), any(), any(), any()))
            .thenReturn(Arrays.asList(dto));

        // Act
        ResponseEntity<List<LeadTimeForChangeDTO>> response = controller.getLeadTimes(team, null, null, null);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(team, response.getBody().get(0).getTeam());
        verify(service).getLeadTimes(team, null, null, null);
    }
} 