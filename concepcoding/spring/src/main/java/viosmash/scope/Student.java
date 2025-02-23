package viosmash.scope;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {
    @Autowired
    private User user;
    public Student() {
        System.out.println("Student initialization");
    }


    @PostConstruct
    public void init() {
        System.out.println("Student has hashCode is: " +  this.hashCode() + "\n"
                + "User has hashCode: " +  this.user.hashCode());
    }

}
