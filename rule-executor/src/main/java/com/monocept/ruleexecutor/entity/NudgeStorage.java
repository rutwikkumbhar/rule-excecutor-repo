package com.monocept.ruleexecutor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monocept.ruleexecutor.model.converters.ListToJsonConvertor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "nudge_storage")
public class NudgeStorage {

    @Id
    @Column(columnDefinition = "UUID")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "nudge_id", columnDefinition = "UUID")
    @JsonProperty("nudge-id")
    private String nudgeId;

    @Column(name = "sender", columnDefinition = "VARCHAR")
    @JsonProperty("sender")
    private String sender;

    @Column(name = "receiver", columnDefinition = "VARCHAR")
    @JsonProperty("receiver")
    private String receiver;

    @Column(name = "process_id", columnDefinition = "VARCHAR")
    @JsonProperty("process-id")
    private String processId;

    @JsonProperty("templates")
    @Column(name = "templates")
    private String templates;

    @Column(name = "count")
    @JsonProperty("count")
    private int count;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP")
    @JsonProperty("created_date")
    private Timestamp createdDate;
}
