package jdbc;

import java.sql.*;
import java.util.List;

/**
 * @author itguoy
 * @date 2021-10-24 15:45
 */
public class JdbcUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/dbtest?characterEncoding=utf8&useSSL=false";

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "123456";


    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static ResultSet query(String sql, Object... args) {
        Connection conn = getConn();
        ResultSet resultSet = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject((i + 1), args[i]);
            }
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;


    }

    public static void closeDB(ResultSet rs, Statement s, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int edit(String sql, Object... args) {
        Connection conn = getConn();
        int rows = 0;
        PreparedStatement ps = null;
        try {
            // 开启事务，关闭自动提交，改成手动提交
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject((i + 1), args[i]);
            }
            rows = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } finally {
            closeDB(null, ps, conn);
        }
        return rows;
    }

    public static void batchEdit(String sql, int argSize, Object... args) {
        Connection conn = getConn();
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            int items = args.length / argSize;
            for (int i = 0; i < items; i++) {
                for (int j = 0; j < argSize; j++) {
                    ps.setObject(j + 1, args[i * argSize + j]);
                }
                // add batch
                ps.addBatch();
                if (i % 5 == 0) {
                    // execute batch when 5 items
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }

            // execute the rest
            if (items % 5 != 0) {
                ps.executeBatch();
                ps.clearBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } finally {
            closeDB(null, ps, conn);
        }

    }


}
