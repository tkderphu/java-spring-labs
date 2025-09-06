package viosmash.test;

import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class SchedulerService {
    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedulerReminder(String message, LocalDateTime start) throws SchedulerException {
        // Unique IDs for job & trigger
        String jobId = UUID.randomUUID().toString();
        String triggerId = UUID.randomUUID().toString();

        // Define the Job
        JobDetail jobDetail = JobBuilder.newJob(GreetJob.class)
                .withIdentity(jobId, "greet-jobs")
                .usingJobData("message", message)
                .build();

        // Set the trigger time (30 sec before start)
        Date triggerTime = Date.from(start.minusSeconds(30)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        // Define the Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerId, "greet-triggers")
                .startAt(triggerTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(6)
                        .withRepeatCount(4))
                .forJob(jobDetail)  // attach job
                .build();

        // Schedule Job + Trigger
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
