package spring01.xml01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author itguoy
 * @date 2021-10-21 17:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clazz {

    private int clazzId;

    private String clazzName;

    private String teacher;

    private List<Student> studentList;

}
