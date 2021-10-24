package com.geek.testschoolstarter;

import com.geek.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TestSchoolStarterApplication {

    @Autowired
    StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(TestSchoolStarterApplication.class, args);

    }

    @GetMapping("/")
    public String hello() {
        return studentService.getStudent();
    }

}
