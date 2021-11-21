package com.geek.hmilydubbotcc.api.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountCny implements Serializable {
    /**
    * 账户id
    */
    private Integer accountId;

    /**
    * 用户id
    */
    private Integer userId;

    /**
    * decimal更好,为方便就用double
    */
    private Double amount;
}