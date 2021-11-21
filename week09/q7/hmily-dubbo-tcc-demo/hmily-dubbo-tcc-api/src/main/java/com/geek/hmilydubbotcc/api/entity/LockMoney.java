package com.geek.hmilydubbotcc.api.entity;

import lombok.Data;

@Data
public class LockMoney {
    /**
    * 自增id
    */
    private Integer id;

    /**
    * 资金来源账户
    */
    private Integer fromAccountId;

    /**
    * 金额
    */
    private Double amount;
}