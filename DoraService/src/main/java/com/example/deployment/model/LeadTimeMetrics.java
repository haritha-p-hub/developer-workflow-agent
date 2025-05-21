package com.example.deployment.model;

import lombok.Data;
import java.util.List;

@Data
public class LeadTimeMetrics {
    private List<Metric> metrics;

    @Data
    public static class Metric {
        private String interval;
        private Double leadTime;
        private Integer count;
        private Double minLeadTime;
        private Double maxLeadTime;
        private Double p95LeadTime;
    }
} 