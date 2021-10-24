package jdbc;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author itguoy
 * @date 2021-10-24 16:06
 */
@Data
@Accessors(chain = true)
public class Student {

    private int id;

    private String name;

}
