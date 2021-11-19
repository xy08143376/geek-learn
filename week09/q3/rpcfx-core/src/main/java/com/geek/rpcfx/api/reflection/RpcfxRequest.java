package com.geek.rpcfx.api.reflection;

import lombok.Data;

/**
 * @author itguoy
 * @date 2021-11-16 15:01
 */

@Data
public class RpcfxRequest<T> {

    private Class<T> serviceClass;

    private String method;

    private Object[] params;

}
