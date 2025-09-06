package viosmash.test;

import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * @author Nguyen Quang Phu
 * @since 05/09/2025
 */
@Service
public class SchedulerService {
    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedulerReminder(
            String message, LocalDateTime start
    ) throws SchedulerException {
        JobDetail jobDetailb = JobBuilder
                .newJob(GreetJob.class)
                .withIdentity("JOB - " + UUID.randomUUID().toString())
                .usingJobData("message", message)
                .build();

        Date triggerTime = Date.from(start.minusSeconds(30)
                .atZone(ZoneId.systemDefault()).toInstant());

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("TRIGGER - " +UUID.randomUUID().toString())
                .startAt(triggerTime)
                .build();

        scheduler.scheduleJob(trigger);
    }
}
