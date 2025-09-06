package viosmash.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author Nguyen Quang Phu
 * @since 05/09/2025
 */
@Component
public class GreetJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Object message = jobExecutionContext.getMergedJobDataMap()
                .get("message");

        System.out.println("Message: " + message);
    }
}
