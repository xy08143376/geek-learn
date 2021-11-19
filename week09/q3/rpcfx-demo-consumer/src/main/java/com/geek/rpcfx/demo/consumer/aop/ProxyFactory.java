package com.geek.rpcfx.demo.consumer.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author itguoy
 * @date 2021-11-17 16:46
 */
public class ProxyFactory {

    private Class serviceClass;

    private BeforeAdvice beforeAdvice;

    private AfterAdvice afterAdvice;

    private Object targetObject;

    public Object createProxy() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class<?>[] interfaces = new Class[]{serviceClass};
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (beforeAdvice != null) {
                    targetObject = beforeAdvice.before(serviceClass);
                }
                System.out.println(targetObject);
                System.out.println(method.getName());
                Object result = method.invoke(targetObject, args);
                if (afterAdvice != null) {
                    afterAdvice.after();
                }
                return result;
            }
        };

        Object proxyObject = Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        return proxyObject;
    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class serviceClass) {
        this.serviceClass = serviceClass;
    }

    public BeforeAdvice getBeforeAdvice() {
        return beforeAdvice;
    }

    public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    public AfterAdvice getAfterAdvice() {
        return afterAdvice;
    }

    public void setAfterAdvice(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }
}
