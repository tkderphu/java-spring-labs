package com.viosmash.service;

import com.viosmash.repository.StudentRepository;
import com.viosmash.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;


    public Flux<Student> findAll() {
        return studentRepository.findAll()
                .delayElements(Duration.ofMillis(300));
    }

    public Flux<Student> findAll(String firstname) {
        return studentRepository.findAllByFirstnameIgnoreCase(firstname);
    }

    public Mono<Student> findById(Integer id) {
        return studentRepository.findById(id);
    }

    public Mono<Student> save(Student student) {
        return studentRepository.save(student);
    }

}
