package viosmash.threadlocal;

import org.springframework.stereotype.Component;

@Component
public class Test {


    public String sayHello() {
        System.out.println("Test component thread: " + Thread.currentThread());
        return "hello guys, this is local -> " + TenantResolver.threadLocal.get();
    }

}
