package com.viosmash;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @GetMapping("/greet")
    public String greet(@RequestParam("message") String message) {
        return message;
    }
}
