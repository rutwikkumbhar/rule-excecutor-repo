package com.monocept.ruleexecutor.schedule.nudges.nudge2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("scheduler.nudge-2")
public class Nudge2SchedulerProperties {
    private String start;
    private String id;
    private Boolean retry;
    private String retryStart;
}
