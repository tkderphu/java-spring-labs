package viosmash.scope;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Default bean has scope is singleton: only one instance is created when app running;
 * prototype: -> is created once has another bean want to inject this bean into it, lazy initialization
 */
@Component
@Scope("prototype")
public class User {
    public User() {
        System.out.println("User initialization");
    }
    @PostConstruct
    public void init() {
        System.out.println("User has hashcode is: " + this.hashCode());
    }
}
