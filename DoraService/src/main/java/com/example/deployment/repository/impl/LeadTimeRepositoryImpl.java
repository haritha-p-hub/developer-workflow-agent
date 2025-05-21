package com.example.deployment.repository.impl;

import com.example.deployment.entity.LeadTimeChange;
import com.example.deployment.model.LeadTimeMetrics;
import com.example.deployment.repository.LeadTimeRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.aggregations.metrics.Percentiles;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LeadTimeRepositoryImpl implements LeadTimeRepository {

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public LeadTimeMetrics getLeadTimeMetrics(LocalDate startDate, LocalDate endDate, String team, String interval) {
        try {
            SearchRequest searchRequest = new SearchRequest("leadtime");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            // Build query
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("startTime")
                    .gte(startDate.atStartOfDay().toInstant(ZoneOffset.UTC))
                    .lte(endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC)))
                .must(QueryBuilders.termQuery("team", team));

            searchSourceBuilder.query(boolQuery);

            // Add aggregations
            String dateHistogramField = "startTime";
            DateHistogramInterval dateHistogramInterval = getDateHistogramInterval(interval);

            searchSourceBuilder.aggregation(
                AggregationBuilders.dateHistogram("by_interval")
                    .field(dateHistogramField)
                    .calendarInterval(dateHistogramInterval)
                    .subAggregation(AggregationBuilders.avg("avg_lead_time").field("leadTime"))
                    .subAggregation(AggregationBuilders.min("min_lead_time").field("leadTime"))
                    .subAggregation(AggregationBuilders.max("max_lead_time").field("leadTime"))
                    .subAggregation(AggregationBuilders.percentiles("p95_lead_time").field("leadTime").percentiles(95))
                    .subAggregation(AggregationBuilders.count("count").field("leadTime"))
            );

            searchRequest.source(searchSourceBuilder);
            SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

            return mapResponseToMetrics(response, interval);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch lead time metrics", e);
        }
    }

    private DateHistogramInterval getDateHistogramInterval(String interval) {
        switch (interval.toLowerCase()) {
            case "daily":
                return DateHistogramInterval.DAY;
            case "weekly":
                return DateHistogramInterval.WEEK;
            case "monthly":
                return DateHistogramInterval.MONTH;
            default:
                throw new IllegalArgumentException("Invalid interval: " + interval);
        }
    }

    private LeadTimeMetrics mapResponseToMetrics(SearchResponse response, String interval) {
        LeadTimeMetrics metrics = new LeadTimeMetrics();
        List<LeadTimeMetrics.Metric> metricList = new ArrayList<>();

        Histogram histogram = response.getAggregations().get("by_interval");
        histogram.getBuckets().forEach(bucket -> {
            LeadTimeMetrics.Metric metric = new LeadTimeMetrics.Metric();
            
            // Format interval key
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                bucket.getKeyAsInstant(), 
                ZoneOffset.UTC
            );
            metric.setInterval(formatInterval(dateTime, interval));
            
            // Set metrics
            Avg avgAgg = bucket.getAggregations().get("avg_lead_time");
            Min minAgg = bucket.getAggregations().get("min_lead_time");
            Max maxAgg = bucket.getAggregations().get("max_lead_time");
            Percentiles p95Agg = bucket.getAggregations().get("p95_lead_time");
            
            metric.setLeadTime(avgAgg.getValue());
            metric.setMinLeadTime(minAgg.getValue());
            metric.setMaxLeadTime(maxAgg.getValue());
            metric.setP95LeadTime(p95Agg.percentile(95));
            metric.setCount((int) bucket.getDocCount());
            
            metricList.add(metric);
        });

        metrics.setMetrics(metricList);
        return metrics;
    }

    private String formatInterval(LocalDateTime dateTime, String interval) {
        DateTimeFormatter formatter;
        switch (interval.toLowerCase()) {
            case "daily":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                break;
            case "weekly":
                formatter = DateTimeFormatter.ofPattern("'Week' w, yyyy");
                break;
            case "monthly":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                break;
            default:
                throw new IllegalArgumentException("Invalid interval: " + interval);
        }
        return dateTime.format(formatter);
    }
} 