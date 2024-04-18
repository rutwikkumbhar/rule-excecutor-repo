package com.monocept.ruleexecutor.schedule.nudges.nudge5;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("scheduler.nudge-5")
public class Nudge5SchedulerProperties {
    private String start;
    private String id;
    private Boolean retry;
    private String retryStart;
}
