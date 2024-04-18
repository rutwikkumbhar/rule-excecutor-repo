package com.monocept.ruleexecutor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ALL")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Nudges {

    @Id
    @Column(columnDefinition = "UUID")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "name", columnDefinition = "VARCHAR")
    @JsonProperty("name")
    private String name;


    @Column(name = "description", columnDefinition = "VARCHAR")
    @JsonProperty("description")
    private String description;

    @Column(name = "category", columnDefinition = "VARCHAR")
    @JsonProperty("category")
    private String category;

    @Column(name = "templates")
    @JsonProperty("templates")
    private List<String> templates;





}
