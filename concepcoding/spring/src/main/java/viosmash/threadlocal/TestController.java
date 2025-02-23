package viosmash.threadlocal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-controller")
public class TestController {
    private final Test test;

    public TestController(Test test) {
        this.test = test;
    }

    @GetMapping
    public String testController() {

        String thread = Thread.currentThread().getName();
        TenantResolver.threadLocal.set(thread);

        return test.sayHello();
    }
}
