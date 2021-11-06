package com.geek.dynamicds.config;

import java.lang.annotation.*;

/**
 * @author itguoy
 * @date 2021-11-06 18:58
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {

    String name();
}
