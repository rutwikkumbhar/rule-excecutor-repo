package com.monocept.ruleexecutor.schedule.nudges.nudge3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("scheduler.nudge-3")
public class Nudge3SchedulerProperties {
    private String start;
    private String id;
    private Boolean retry;
    private String retryStart;
}
