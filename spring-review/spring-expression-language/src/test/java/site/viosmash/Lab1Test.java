package site.viosmash;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootApplication
class Lab1Test {

    @Autowired
    private Lab1 lab1;
    @Test
    void getValue1() {

        System.out.println(lab1.getValue1());
    }

    @Test
    void getValue2() {
    }

    @Test
    void getValue3() {
    }
}