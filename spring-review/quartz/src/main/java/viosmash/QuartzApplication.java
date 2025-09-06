package viosmash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import viosmash.test.SchedulerService;

import java.time.LocalDateTime;

/**
 * @author Nguyen Quang Phu
 * @since 05/09/2025
 */
@SpringBootApplication
public class QuartzApplication implements CommandLineRunner {
    @Autowired
    private SchedulerService schedulerService;

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        schedulerService.schedulerReminder(
                "hello world",
                LocalDateTime.now().plusMinutes(1)
        );
    }
}
