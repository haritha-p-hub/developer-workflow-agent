package com.example.deployment.repository.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.example.deployment.entity.DeploymentFrequencyEntity;
import com.example.deployment.entity.LeadTimeForChangeEntity;
import com.example.deployment.repository.DoraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AvgAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.ValueCountAggregation;
import co.elastic.clients.elasticsearch.core.search.Aggregate;
import co.elastic.clients.elasticsearch.core.search.Bucket;
import java.util.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoraRepositoryImpl implements DoraRepository {
    private static final Logger logger = LoggerFactory.getLogger(DoraRepositoryImpl.class);
    private final ElasticsearchClient client;

    public DoraRepositoryImpl(ElasticsearchClient client) {
        this.client = client;
    }

    @Override
    public List<DeploymentFrequencyEntity> fetchDeploymentFrequency(String index) {
        try {
            // First check if the index exists
            BooleanResponse indexExists = client.indices().exists(e -> e.index(index));
            if (!indexExists.value()) {
                logger.error("Index {} does not exist", index);
                throw new RuntimeException("Index " + index + " does not exist");
            }

            logger.debug("Fetching deployment frequency from index: {}", index);
            SearchRequest request = SearchRequest.of(r -> r
                .index(index)
                .size(0)
                .aggregations("by_day", a -> a
                    .dateHistogram(dh -> dh
                        .field("@timestamp")
                        .calendarInterval(CalendarInterval.Day)
                    )
                    .aggregations("deployment_count", ac -> ac
                        .filter(f -> f
                            .term(t -> t
                                .field("status")
                                .value("success")
                            )
                        )
                    )
                )
            );

            SearchResponse<Void> response = client.search(request, Void.class);
            List<DeploymentFrequencyEntity> results = new ArrayList<>();
            
            response.aggregations()
                .get("by_day")
                .dateHistogram()
                .buckets().array()
                .forEach(bucket -> {
                    String day = bucket.keyAsString();
                    long count = bucket.aggregations()
                        .get("deployment_count")
                        .filter()
                        .docCount();
                    results.add(new DeploymentFrequencyEntity(day, count));
                    logger.debug("Found {} deployments on {}", count, day);
                });
                
            return results;
        } catch (IOException e) {
            logger.error("Failed to fetch deployment frequency", e);
            throw new RuntimeException("Failed to fetch deployment frequency: " + e.getMessage(), e);
        }
    }

    @Override
    public List<LeadTimeForChangeEntity> fetchLeadTimeForChange(String index, String startDate, String endDate, String team, String interval) {
        List<LeadTimeForChangeEntity> results = new ArrayList<>();
        try {
            BooleanResponse indexExists = client.indices().exists(e -> e.index(index));
            if (!indexExists.value()) {
                logger.error("Index {} does not exist", index);
                throw new RuntimeException("Index " + index + " does not exist");
            }

            logger.debug("Fetching lead time for change from index: {}", index);
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                .index(index)
                .size(0)
                .query(q -> q
                    .bool(b -> {
                        b.filter(f -> f.range(r -> r.field("deployed_at").gte(startDate).lte(endDate)));
                        if (team != null && !team.isEmpty()) {
                            b.filter(f -> f.term(t -> t.field("team").value(team)));
                        }
                        return b;
                    })
                )
                .aggregations("intervals", a -> a
                    .dateHistogram(dh -> dh
                        .field("deployed_at")
                        .calendarInterval(CalendarInterval.valueOf(interval.toUpperCase()))
                    )
                    .aggregations("lead_time", ag -> ag
                        .avg(avg -> avg
                            .script(s -> s
                                .source("(doc['deployed_at'].value.toInstant().toEpochMilli() - doc['created_at'].value.toInstant().toEpochMilli()) / 86400000")
                            )
                        )
                    )
                    .aggregations("changes", ag -> ag
                        .valueCount(vc -> vc.field("_id"))
                    )
                );

            SearchResponse<Void> response = client.search(requestBuilder.build(), Void.class);
            response.aggregations()
                .get("intervals")
                .dateHistogram()
                .buckets().array()
                .forEach(bucket -> {
                    String key = bucket.keyAsString();
                    double leadTime = 0.0;
                    int changes = 0;
                    if (bucket.aggregations().get("lead_time").avg().value() != null) {
                        leadTime = bucket.aggregations().get("lead_time").avg().value();
                    }
                    if (bucket.aggregations().get("changes").valueCount().value() != null) {
                        changes = bucket.aggregations().get("changes").valueCount().value().intValue();
                    }
                    results.add(new LeadTimeForChangeEntity(key, leadTime, changes));
                });
            return results;
        } catch (IOException e) {
            logger.error("Failed to fetch lead time for change", e);
            throw new RuntimeException("Failed to fetch lead time for change: " + e.getMessage(), e);
        }
    }
} 