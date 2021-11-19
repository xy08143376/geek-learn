package com.geek.rpcfx.api.reflection;

/**
 * @author itguoy
 * @date 2021-11-16 15:03
 */
public interface RpcfxResolver {

    <T> T resolve(Class<T> serviceClass);

}
