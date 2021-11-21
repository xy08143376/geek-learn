package com.geek.hmilydubbotcc.api.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDollar implements Serializable {
    /**
     * 自增主键
     */
    private Integer accountId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * decimal更好，为了方便用double
     */
    private Double amount;
}