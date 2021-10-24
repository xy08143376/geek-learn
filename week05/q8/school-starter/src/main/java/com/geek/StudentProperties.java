package com.geek;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author itguoy
 * @date 2021-10-24 14:46
 */
@Data
@ConfigurationProperties(prefix = "geek.stu")
public class StudentProperties {

    private int id = 1;

    private String name = "geek";

}
