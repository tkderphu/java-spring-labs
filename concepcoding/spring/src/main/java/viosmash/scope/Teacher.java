package viosmash.scope;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Teacher {
    @Autowired
    private User user;
    public Teacher() {
        System.out.println("Teacher initialization");
    }
    @PostConstruct
    public void init() {
        System.out.println("Teacher has hashCode is: " +  this.hashCode() + "\n"
                + "User has hashCode: " +  this.user.hashCode());
    }
}
