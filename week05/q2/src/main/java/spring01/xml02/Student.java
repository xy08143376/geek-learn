package spring01.xml02;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author itguoy
 * @date 2021-10-21 19:14
 */
@Data
@Accessors(chain = true)
public class Student {

    private int age;

    private String name;


}
