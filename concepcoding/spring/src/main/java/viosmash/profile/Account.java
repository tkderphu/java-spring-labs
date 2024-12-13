package viosmash.profile;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Account {
    @Value("${test}")
    private String username;
    @Value("${password}")
    private String password;
    @PostConstruct
    public void init() {
        System.out.println(username + " - " + password);
    }
}
