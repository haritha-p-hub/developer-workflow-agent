package com.example.deployment.controller;

import com.example.deployment.model.LeadTimeMetrics;
import com.example.deployment.service.LeadTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeadTimeControllerTest {

    @Mock
    private LeadTimeService leadTimeService;

    @InjectMocks
    private LeadTimeController leadTimeController;

    private LocalDate startDate;
    private LocalDate endDate;
    private String team;
    private String interval;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 1, 31);
        team = "team1";
        interval = "daily";
    }

    @Test
    void getLeadTimeMetrics_WithValidInputs_ShouldReturnMetrics() {
        // Arrange
        LeadTimeMetrics expectedMetrics = new LeadTimeMetrics();
        when(leadTimeService.getLeadTimeMetrics(any(), any(), any(), any()))
            .thenReturn(expectedMetrics);

        // Act
        ResponseEntity<LeadTimeMetrics> response = leadTimeController.getLeadTimeMetrics(
            startDate, endDate, team, interval
        );

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedMetrics, response.getBody());
        verify(leadTimeService).getLeadTimeMetrics(startDate, endDate, team, interval);
    }

    @Test
    void getLeadTimeMetrics_WithDefaultInterval_ShouldUseDaily() {
        // Arrange
        LeadTimeMetrics expectedMetrics = new LeadTimeMetrics();
        when(leadTimeService.getLeadTimeMetrics(any(), any(), any(), eq("daily")))
            .thenReturn(expectedMetrics);

        // Act
        ResponseEntity<LeadTimeMetrics> response = leadTimeController.getLeadTimeMetrics(
            startDate, endDate, team, null
        );

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(expectedMetrics, response.getBody());
        verify(leadTimeService).getLeadTimeMetrics(startDate, endDate, team, "daily");
    }

    @Test
    void getLeadTimeMetrics_WhenServiceThrowsException_ShouldPropagateException() {
        // Arrange
        when(leadTimeService.getLeadTimeMetrics(any(), any(), any(), any()))
            .thenThrow(new IllegalArgumentException("Invalid input"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            leadTimeController.getLeadTimeMetrics(startDate, endDate, team, interval)
        );
    }
} 