package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author itguoy
 * @date 2021-10-24 16:34
 */
public class JdbcTest {

    public static void main(String[] args) throws SQLException {
        // insert
        String name = "Tom";
        String sql1 = "insert into t_student(name) values (?)";
        JdbcUtil.edit(sql1, name);

        // batch insert
        String[] names = {"Jack", "Jenny", "Tom"};
        StringBuilder builder = new StringBuilder("insert into t_student(name) values (?)");
        JdbcUtil.batchEdit(builder.toString(), 1, names);

        // query
        int queryId = 2;
        String sql2 = "select * from t_student where id=?";
        ResultSet resultSet = JdbcUtil.query(sql2, queryId);
        Student stu = null;
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name1 = resultSet.getString("name");
            stu = new Student();
            stu.setId(id).setName(name1);
        }
        System.out.println(stu);
        JdbcUtil.closeDB(resultSet, resultSet.getStatement(), resultSet.getStatement().getConnection());


        // queryList
        String queryName = "Tom";
        String sql3 = "select * from t_student where name=?";
        ResultSet rs = JdbcUtil.query(sql3, queryName);
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
        JdbcUtil.closeDB(rs, rs.getStatement(), rs.getStatement().getConnection());
    }

}
