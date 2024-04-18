package com.monocept.ruleexecutor.schedule.nudges.nudge4;


import com.monocept.ruleexecutor.service.PrepareEventsService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@EnableConfigurationProperties({Nudge4SchedulerProperties.class})
public class Nudge4ScheduleTask {

    private final Nudge4SchedulerProperties schedulerProperties;

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final PrepareEventsService prepareEventsService;

    @Value("${scheduler.retry}")
    private Boolean isGlobalRetry;

    public Nudge4ScheduleTask(Nudge4SchedulerProperties schedulerProperties,
                              ThreadPoolTaskScheduler threadPoolTaskScheduler,
                              PrepareEventsService prepareEventsService) {
        this.schedulerProperties = schedulerProperties;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.prepareEventsService = prepareEventsService;
    }

    @PostConstruct
    public void scheduleJob() {
        log.debug("Schedule job started");
        Runnable job = this::triggerJob;
        threadPoolTaskScheduler.schedule(job, new CronTrigger(schedulerProperties.getStart()));
        if (isGlobalRetry && Optional.ofNullable(schedulerProperties.getRetry()).orElse(false)) {
            threadPoolTaskScheduler.schedule(job, new CronTrigger(schedulerProperties.getRetryStart()));
        }
    }

    public void triggerJob() {
        log.debug("triggered job");
        prepareEventsService.prepareEvents(schedulerProperties.getId());
    }
}
