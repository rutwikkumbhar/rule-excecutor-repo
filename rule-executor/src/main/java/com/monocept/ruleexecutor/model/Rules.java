package com.monocept.ruleexecutor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Rules {

    @JsonProperty("type")
    private String type;

    @JsonProperty("NO_OF_NUDGES_PER_DAY_PER_PERSON")
    private Integer numberOfNudgesPerDayPerPerson;

    @JsonProperty("DDupe_check")
    private List<String> dDupeCheck;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("cta")
    private String cta;

    @JsonProperty("landingPage")
    private String landingPage;

    @JsonProperty("receiver")
    private String receiver;

    @JsonProperty("templates")
    private List<Templates> templates;
}
