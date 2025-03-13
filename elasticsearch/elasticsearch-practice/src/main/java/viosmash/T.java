package viosmash;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class T {

    @EventListener
    public void listen(String s) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(Thread.currentThread() + " hello world");
    }

}
