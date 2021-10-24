package spring01.xml02;

import com.sun.xml.internal.ws.util.pipe.StandaloneTubeAssembler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itguoy
 * @date 2021-10-21 19:14
 */

@Service
public class StudentService {

    public List<Student> listStudent() {
        List<Student> studentList = new ArrayList<>();
        Student student01 = new Student().setAge(19).setName("cat");
        Student student02 = new Student().setAge(20).setName("Peter");
        studentList.add(student01);
        studentList.add(student02);
        return studentList;
    }

}
