package spring01.xml01;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author itguoy
 * @date 2021-10-21 17:52
 */
public class Xml01Main {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlApplicationContext01.xml");
        Student student01 = applicationContext.getBean("student01", Student.class);
        System.out.println(student01);

        Clazz clazz = applicationContext.getBean(Clazz.class);
        System.out.println(clazz);
    }
}
