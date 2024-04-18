package com.monocept.ruleexecutor.schedule.nudges.nudge1;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("scheduler.nudge-1")
public class Nudge1SchedulerProperties {
    private String start;
    private String id;
    private Boolean retry;
    private String retryStart;
}
