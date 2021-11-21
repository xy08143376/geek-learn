package com.geek.hmilydubbotcc.api.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CancelLog {
    /**
    * 事务id
    */
    private String txNo;

    private LocalDateTime createTime;
}