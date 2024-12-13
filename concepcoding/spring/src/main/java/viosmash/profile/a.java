package viosmash.profile;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") //only profile is dev then spring will create this bean
public class a {
    @Value("${test}")
    private String username;
    @Value("${password}")
    private String password;
    @PostConstruct
    public void init() {
        System.out.println(getClass().getSimpleName() + " " +username + " - " + password);
    }
}
