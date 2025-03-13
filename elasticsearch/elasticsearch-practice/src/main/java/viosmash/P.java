package viosmash;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class P {
    private final ApplicationContext applicationContext;


    public P(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

    }

    public void push() {
        System.out.println(Thread.currentThread() + " push event");
        applicationContext.publishEvent("hello world");
        System.out.println("end event");
    }
}
