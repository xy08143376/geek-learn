package jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author itguoy
 * @date 2021-10-24 17:12
 */
public class HikariDBUtil {

    public static HikariDataSource getDataSource() {
        try (InputStream is = HikariDBUtil.class.getClassLoader().getResourceAsStream("hikari.properties")) {
            Properties props = new Properties();
            props.load(is);
            HikariConfig config = new HikariConfig(props);
            HikariDataSource dataSource = new HikariDataSource(config);
            return dataSource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConn() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        }

    }


}
