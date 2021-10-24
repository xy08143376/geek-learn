package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author itguoy
 * @date 2021-10-24 17:18
 */
public class HikariTest {

    public static void main(String[] args) throws SQLException {
        // insert
        String name = "Hikari-Tom";
        String sql1 = "insert into t_student(name) values (?)";
        HikariDBUtil.edit(sql1, name);

        // batch insert
        String[] names = {"Hikari-Jack", "Hikari-Jenny", "Hikari-Tom"};
        StringBuilder builder = new StringBuilder("insert into t_student(name) values (?)");
        HikariDBUtil.batchEdit(builder.toString(), 1, names);

        // query
        Random random = new Random();
        int queryId = random.nextInt(50);
        String sql2 = "select * from t_student where id=?";
        ResultSet resultSet = HikariDBUtil.query(sql2, queryId);
        Student stu = null;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name1 = resultSet.getString("name");
            stu = new Student();
            stu.setId(id).setName(name1);
        }
        System.out.println(stu);


        // queryList
        String queryName = "Hikari-Tom";
        String sql3 = "select * from t_student where name=?";
        ResultSet rs = HikariDBUtil.query(sql3, queryName);
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name1 = rs.getString("name");
            Student student = new Student();
            student.setId(id).setName(name1);
            students.add(student);
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
