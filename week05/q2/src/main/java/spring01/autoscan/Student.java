package spring01.autoscan;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author itguoy
 * @date 2021-10-21 18:21
 */
@Data
@Accessors(chain = true)
public class Student {

    private int age;

    private String name;

}
