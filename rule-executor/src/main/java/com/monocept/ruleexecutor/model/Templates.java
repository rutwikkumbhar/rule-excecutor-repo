package com.monocept.ruleexecutor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;


@SuppressWarnings("ALL")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "templates")
@ToString
public class Templates {

    @Id
    @Column(columnDefinition = "UUID")
    @JsonProperty("id")
    private String id;

    @Column(name = "name", columnDefinition = "VARCHAR")
    @JsonProperty("name")
    private String name;

    @Column(name = "condition", columnDefinition = "VARCHAR")
    @JsonProperty("condition")
    private String condition;


    @Column(name = "description", columnDefinition = "VARCHAR")
    @JsonProperty("description")
    private String description;

    @Column(name = "message_content", columnDefinition = "VARCHAR")
    @JsonProperty("message_content")
    private String messageContent;

    @Column(name = "type", columnDefinition = "VARCHAR")
    @JsonProperty("type")
    private String templateType;
}
