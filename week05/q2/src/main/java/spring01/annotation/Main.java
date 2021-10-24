package spring01.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author itguoy
 * @date 2021-10-21 18:12
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        Student student01 = applicationContext.getBean("student01", Student.class);
        System.out.println(student01);

        Clazz clazz = applicationContext.getBean(Clazz.class);
        System.out.println(clazz);
    }
}
