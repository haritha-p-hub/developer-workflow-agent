package com.example.deployment.repository.impl;

import com.example.deployment.entity.LeadTimeChange;
import com.example.deployment.model.LeadTimeMetrics;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.aggregations.metrics.Percentiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeadTimeRepositoryImplTest {

    @Mock
    private RestHighLevelClient elasticsearchClient;

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private LeadTimeRepositoryImpl leadTimeRepository;

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
    void getLeadTimeMetrics_ShouldReturnMetrics() throws Exception {
        // Arrange
        SearchResponse mockResponse = mock(SearchResponse.class);
        Aggregations mockAggregations = mock(Aggregations.class);
        Histogram mockHistogram = mock(Histogram.class);
        List<Histogram.Bucket> buckets = createMockBuckets();

        when(elasticsearchClient.search(any(), any())).thenReturn(mockResponse);
        when(mockResponse.getAggregations()).thenReturn(mockAggregations);
        when(mockAggregations.get("by_interval")).thenReturn(mockHistogram);
        when(mockHistogram.getBuckets()).thenReturn(buckets);

        // Act
        LeadTimeMetrics result = leadTimeRepository.getLeadTimeMetrics(startDate, endDate, team, interval);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getMetrics());
        assertEquals(2, result.getMetrics().size());
        verify(elasticsearchClient).search(any(), any());
    }

    @Test
    void getLeadTimeMetrics_WithInvalidInterval_ShouldThrowException() {
        // Arrange
        String invalidInterval = "invalid";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            leadTimeRepository.getLeadTimeMetrics(startDate, endDate, team, invalidInterval)
        );
    }

    private List<Histogram.Bucket> createMockBuckets() {
        List<Histogram.Bucket> buckets = new ArrayList<>();
        
        // Create first bucket
        Histogram.Bucket bucket1 = mock(Histogram.Bucket.class);
        when(bucket1.getKeyAsInstant()).thenReturn(
            LocalDateTime.of(2024, 1, 1, 0, 0).toInstant(ZoneOffset.UTC)
        );
        when(bucket1.getDocCount()).thenReturn(5L);
        
        Avg avg1 = mock(Avg.class);
        when(avg1.getValue()).thenReturn(2.5);
        Min min1 = mock(Min.class);
        when(min1.getValue()).thenReturn(1.0);
        Max max1 = mock(Max.class);
        when(max1.getValue()).thenReturn(4.0);
        Percentiles p951 = mock(Percentiles.class);
        when(p951.percentile(95)).thenReturn(3.8);
        
        when(bucket1.getAggregations()).thenReturn(mock(Aggregations.class));
        when(bucket1.getAggregations().get("avg_lead_time")).thenReturn(avg1);
        when(bucket1.getAggregations().get("min_lead_time")).thenReturn(min1);
        when(bucket1.getAggregations().get("max_lead_time")).thenReturn(max1);
        when(bucket1.getAggregations().get("p95_lead_time")).thenReturn(p951);
        
        buckets.add(bucket1);

        // Create second bucket
        Histogram.Bucket bucket2 = mock(Histogram.Bucket.class);
        when(bucket2.getKeyAsInstant()).thenReturn(
            LocalDateTime.of(2024, 1, 2, 0, 0).toInstant(ZoneOffset.UTC)
        );
        when(bucket2.getDocCount()).thenReturn(3L);
        
        Avg avg2 = mock(Avg.class);
        when(avg2.getValue()).thenReturn(3.0);
        Min min2 = mock(Min.class);
        when(min2.getValue()).thenReturn(2.0);
        Max max2 = mock(Max.class);
        when(max2.getValue()).thenReturn(4.0);
        Percentiles p952 = mock(Percentiles.class);
        when(p952.percentile(95)).thenReturn(3.9);
        
        when(bucket2.getAggregations()).thenReturn(mock(Aggregations.class));
        when(bucket2.getAggregations().get("avg_lead_time")).thenReturn(avg2);
        when(bucket2.getAggregations().get("min_lead_time")).thenReturn(min2);
        when(bucket2.getAggregations().get("max_lead_time")).thenReturn(max2);
        when(bucket2.getAggregations().get("p95_lead_time")).thenReturn(p952);
        
        buckets.add(bucket2);

        return buckets;
    }
} 