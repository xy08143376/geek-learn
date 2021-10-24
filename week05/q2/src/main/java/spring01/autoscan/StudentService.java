package spring01.autoscan;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itguoy
 * @date 2021-10-21 18:20
 */
@Service
public class StudentService {

    public List<Student> listStudent() {
        List<Student> studentList = new ArrayList<>();

        Student student01 = new Student().setAge(18).setName("Tom");
        Student student02 = new Student().setAge(20).setName("Jack");
        studentList.add(student02);
        studentList.add(student01);

        return studentList;
    }

}
