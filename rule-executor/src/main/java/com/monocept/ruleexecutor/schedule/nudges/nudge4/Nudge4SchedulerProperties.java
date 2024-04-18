package com.monocept.ruleexecutor.schedule.nudges.nudge4;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("scheduler.nudge-4")
public class Nudge4SchedulerProperties {
    private String start;
    private String id;
    private Boolean retry;
    private String retryStart;
}
