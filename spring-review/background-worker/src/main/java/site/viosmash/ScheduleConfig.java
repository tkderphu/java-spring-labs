package site.viosmash;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduleConfig {

    @Scheduled(  cron = "10 0 0 0 0 0")
    public void writeCurrentTime() {
        String thread = Thread.currentThread().getName();;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();

        String nowString = df.format(now);

        System.out.println(thread + "run background now is: "+ nowString);
    }

}
