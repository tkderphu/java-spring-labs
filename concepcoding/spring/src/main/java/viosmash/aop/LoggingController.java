package viosmash.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logging")
public class LoggingController {

    @GetMapping("/get")
    public String get() {
        return "get log";
    }

}
