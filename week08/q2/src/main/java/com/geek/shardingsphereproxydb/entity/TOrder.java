package com.geek.shardingsphereproxydb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName(value = "t_order")
@Accessors(chain = true)
@Data
public class TOrder implements Serializable {
    /**
     * 订单id，自增
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 商品id
     */
    @TableField(value = "prod_id")
    private Long prodId;

    /**
     * 下单价格，即单价
     */
    @TableField(value = "order_price")
    private BigDecimal orderPrice;

    /**
     * 下单数量
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 订单总价
     */
    @TableField(value = "order_total_price")
    private BigDecimal orderTotalPrice;

    /**
     * 订单状态，1已支付2未支付3已完成4已取消
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PROD_ID = "prod_id";

    public static final String COL_ORDER_PRICE = "order_price";

    public static final String COL_ORDER_NUM = "order_num";

    public static final String COL_ORDER_TOTAL_PRICE = "order_total_price";

    public static final String COL_ORDER_STATUS = "order_status";

}