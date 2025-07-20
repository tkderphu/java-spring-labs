package com.viosmash;

import com.viosmash.service.StudentService;
import com.viosmash.student.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


//    @Bean
//    public CommandLineRunner run(StudentService studentService) {
//        return args -> {
//            for(int i = 0; i < 100; i++) {
//                studentService.save(new Student(
//                        i, "Phu " + i, "Nguyen " + i, i
//                )).subscribe();
//            }
//        };
//    }

}