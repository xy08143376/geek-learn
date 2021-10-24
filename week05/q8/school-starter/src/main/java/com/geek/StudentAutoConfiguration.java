package com.geek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author itguoy
 * @date 2021-10-24 14:45
 */
@Configuration
@EnableConfigurationProperties(StudentProperties.class)
public class StudentAutoConfiguration {

    @Autowired
    private StudentProperties studentProperties;

    @Bean
    public StudentService studentService() {
        StudentService studentService = new StudentService();
        studentService.setStudentProperties(studentProperties);
        return studentService;
    }

}
