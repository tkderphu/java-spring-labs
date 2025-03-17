package viosmash;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringSecurityApplication {
    @Bean("ss")
    public SecurityService securityService() {
        return new SecurityService();
    }
}
