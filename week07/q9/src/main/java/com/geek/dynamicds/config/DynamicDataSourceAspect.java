package com.geek.dynamicds.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author itguoy
 * @date 2021-11-06 18:54
 */

@Aspect
@Component
@Order(-1)
public class DynamicDataSourceAspect {

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource ds) {
        //
        String dsKey = ds.name();

        DynamicDataSourceContextHolder.setDataSourceType(dsKey);
    }

    @After("@annotation(ds)")
    public void end(JoinPoint joinPoint, TargetDataSource ds) {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
