package spring01.xml02;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author itguoy
 * @date 2021-10-21 19:17
 */
public class Main {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("xmlApplicationContext02.xml");

        StudentService studentService = ctx.getBean(StudentService.class);
        System.out.println(studentService.listStudent());

    }
}
