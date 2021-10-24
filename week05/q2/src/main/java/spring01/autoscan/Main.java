package spring01.autoscan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author itguoy
 * @date 2021-10-21 19:11
 */
public class Main {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        StudentService studentService = applicationContext.getBean("studentService", StudentService.class);
        System.out.println(studentService.listStudent());

    }
}
