package com.viosmash.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class Controller {

    @GetMapping("/messages")
    public Flux<String> getMessage() {
        System.out.println("Current thread start: " + Thread.currentThread());

        Flux<String> stringFlux = Flux.fromArray(new String[]{
                "121212\n ", " dsadasdsad",
        }).delayElements(Duration.ofSeconds(10));
        System.out.println("Current thread end: " + Thread.currentThread());

        return stringFlux;
    }
}
