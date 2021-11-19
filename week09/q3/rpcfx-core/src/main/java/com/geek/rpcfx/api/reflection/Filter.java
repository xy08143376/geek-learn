package com.geek.rpcfx.api.reflection;

/**
 * @author itguoy
 * @date 2021-11-16 15:11
 */
public interface Filter {

    boolean filter(RpcfxRequest request);

}
