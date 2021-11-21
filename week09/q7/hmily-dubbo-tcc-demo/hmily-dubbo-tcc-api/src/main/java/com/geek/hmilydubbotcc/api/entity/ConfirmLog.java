package com.geek.hmilydubbotcc.api.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ConfirmLog {
    /**
    * 事务id
    */
    private String txNo;

    private LocalDateTime createTime;
}