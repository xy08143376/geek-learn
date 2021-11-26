package com.geek.redispubsub.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author itguoy
 * @date 2021-11-25 15:48
 */
@Data
public class Order implements Serializable {

    private int orderId;

    private int prodId;

    private int prodNum;

    private double orderAmount;

    private Date orderTime;

}
