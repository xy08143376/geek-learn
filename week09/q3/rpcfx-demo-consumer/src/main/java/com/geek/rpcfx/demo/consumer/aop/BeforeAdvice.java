package com.geek.rpcfx.demo.consumer.aop;

/**
 * @author itguoy
 * @date 2021-11-17 16:47
 */
public interface BeforeAdvice {

    Object before(Class serviceClass) throws Exception;

}
