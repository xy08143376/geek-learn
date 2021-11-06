package week07;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author itguoy
 * @date 2021-11-06 16:13
 */
public class InsertTest {

    public static Random random = new Random();

    public static void main(String[] args) throws SQLException, InterruptedException {


        List<Order> orderList = createOrder(1000000);
//        insert2(orderList);

        orderList = createOrder(1000000);
        insert3(orderList);

        orderList = createOrder(1000000);
        insert4(orderList);


    }


    public static void insert2(List<Order> orderList) throws SQLException {
        long start = System.currentTimeMillis();
        Connection conn = HikariDBUtil.getConn();
        conn.setAutoCommit(false);
        try {
            String insertSql = "insert into t_order(user_id,prod_id,order_price,order_num,order_total_price,order_status) values  ";
            StringBuilder sql = new StringBuilder(insertSql);

            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                sql.append("(").append(order.getUser_id()).append(",")
                        .append(order.getProd_id()).append(",")
                        .append(order.getOrder_price()).append(",")
                        .append(order.getOrder_num()).append(",")
                        .append(order.getOrder_total_price()).append(",")
                        .append(order.getOrder_status())
                        .append("),");
                if (i % 100000 == 0) {
                    sql.deleteCharAt(sql.lastIndexOf(","));
                    PreparedStatement ps = conn.prepareStatement(sql.toString());
                    ps.executeUpdate();
                    conn.commit();
                    sql = new StringBuilder(insertSql);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }

        long end = System.currentTimeMillis();
        System.out.println("multi value timeout: " + (end - start) + " ms");

    }

    public static void insert3(List<Order> orderList) throws SQLException {
        long start = System.currentTimeMillis();
        Connection conn = HikariDBUtil.getConn();
        conn.setAutoCommit(false);
        try {
            String insertSql = "insert into t_order(user_id,prod_id,order_price,order_num,order_total_price,order_status) values (?,?,?,?,?,?)";
            StringBuilder sql = new StringBuilder(insertSql);
            int size = 10000;
            int batch = 1000000 / size;

            for (int i = 0; i < batch; i++) {
                PreparedStatement ps = conn.prepareStatement(insertSql);
                for (int j = 0; j < size; j++) {
                    Order order = orderList.get(i * size + j);
                    ps.setObject(1, order.getUser_id());
                    ps.setObject(2, order.getProd_id());
                    ps.setObject(3, order.getOrder_price());
                    ps.setObject(4, order.getOrder_num());
                    ps.setObject(5, order.getOrder_total_price());
                    ps.setObject(6, order.getOrder_status());
                    ps.addBatch();
                }
                ps.executeBatch();

                System.out.println("batch " + i + " finish...");
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }

        long end = System.currentTimeMillis();
        System.out.println("add batch timeout: " + (end - start) + " ms");
    }

    public static void insert4(List<Order> orderList) throws InterruptedException {
        long begin = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            int start = i * 200000;
            int end = (i + 1) * 200000;
            List<Order> subList = orderList.subList(start, end);
            new Thread(() -> {
                try {
                    insert2(subList);
                    latch.countDown();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("multi thread timeout: " + (end - begin) + " ms");
    }

    public static void clearData() throws SQLException {
        Connection conn = HikariDBUtil.getConn();
        String sql = "TRUNCATE TABLE t_order";
        PreparedStatement ps = conn.prepareStatement(sql);

    }


    public static List<Order> createOrder(int size) {
        List<Order> orderList = new ArrayList<>(size);


        for (int i = 0; i < size; i++) {
            Order order = new Order();
            BigDecimal price = BigDecimal.valueOf((10 + random.nextInt(10000)) / 10.0);
            int orderNum = random.nextInt(10) + 1;
            order.setOrder_num(orderNum)
                    .setOrder_price(price)
                    .setOrder_total_price(price.multiply(BigDecimal.valueOf(orderNum)))
                    .setUser_id(random.nextInt(3) + 1)
                    .setProd_id(random.nextInt(10000))
                    .setOrder_status(random.nextInt(5));

            orderList.add(order);
        }


        return orderList;
    }
}
