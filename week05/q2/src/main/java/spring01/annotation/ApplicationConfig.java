package spring01.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itguoy
 * @date 2021-10-21 18:07
 */

@Configuration
public class ApplicationConfig {

    @Bean("student01")
    public Student student01() {
        return new Student().setAge(18).setName("张三");
    }

    @Bean("student02")
    public Student student02() {
        return new Student().setAge(20).setName("李四");
    }

    @Bean
    public Clazz clazz() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student01());
        studentList.add(student02());
        return new Clazz().setClazzId(1).setClazzName("三年一班")
                .setTeacher("刘老师").setStudentList(studentList);
    }

}
