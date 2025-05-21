package com.example.deployment.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Document(indexName = "leadtime")
public class LeadTimeChange {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String team;

    @Field(type = FieldType.Date)
    private LocalDateTime startTime;

    @Field(type = FieldType.Date)
    private LocalDateTime endTime;

    @Field(type = FieldType.Double)
    private Double leadTime;

    @Field(type = FieldType.Keyword)
    private String status;
} 