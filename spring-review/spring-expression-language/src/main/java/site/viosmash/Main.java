package site.viosmash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private Lab1 lab1;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("is true: " + lab1.getTrue());
        System.out.println(lab1.getValue1());
    }
}
