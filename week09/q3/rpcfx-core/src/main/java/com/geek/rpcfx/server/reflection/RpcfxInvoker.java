package com.geek.rpcfx.server.reflection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.geek.rpcfx.api.RpcfxResponse;
import com.geek.rpcfx.api.reflection.RpcfxRequest;
import com.geek.rpcfx.api.reflection.RpcfxResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author itguoy
 * @date 2021-11-16 15:40
 */
public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        Class serviceClass = request.getServiceClass();


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
