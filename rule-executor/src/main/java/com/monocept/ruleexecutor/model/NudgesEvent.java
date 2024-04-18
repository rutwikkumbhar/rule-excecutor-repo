package com.monocept.ruleexecutor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NudgesEvent{
    @JsonProperty("nudge-id")
    String nudgeId;
    @JsonProperty("user-id")
    String userId;
    @JsonProperty("sender")
    String sender;
    @JsonProperty("cta")
    String cta;
    @JsonProperty("landingPage")
    String landingPage;
    @JsonProperty("receiver")
    String receiver;
    @JsonProperty("deviceId")
    String deviceId;
    @JsonProperty("template")
    Templates template;
    @JsonProperty("count")
    Integer count;
    public String getId(String date) {
        return userId + "_" +nudgeId + "_" + date;
    }
}
