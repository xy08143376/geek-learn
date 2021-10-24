package spring01.annotation;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author itguoy
 * @date 2021-10-21 18:01
 */
@Data
@Accessors(chain = true)
public class Student {

    private String name;

    private int age;

}
