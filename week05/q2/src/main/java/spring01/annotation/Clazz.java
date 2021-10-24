package spring01.annotation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author itguoy
 * @date 2021-10-21 18:09
 */
@Data
@Accessors(chain = true)
public class Clazz {

    private int clazzId;

    private String clazzName;

    private String teacher;

    private List<Student> studentList;

}
