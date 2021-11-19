package com.geek.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.geek.rpcfx.api.RpcfxRequest;
import com.geek.rpcfx.api.RpcfxResolver;
import com.geek.rpcfx.api.RpcfxResponse;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author itguoy
 * @date 2021-11-16 10:32
 */
public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();


        // 作业1: 改成泛型和反射
        Object service = resolver.resolve(serviceClass);

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams());
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }

    }

    private Method resolveMethodFromClass(Class<?> aClass, String methodName) {
        return Arrays.stream(aClass.getMethods())
                .filter(m -> methodName.equals(m.getName()))
                .findFirst().get();
    }
}
