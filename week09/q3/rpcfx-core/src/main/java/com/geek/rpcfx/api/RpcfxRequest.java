package com.geek.rpcfx.api;

import lombok.Data;

/**
 * @author itguoy
 * @date 2021-11-16 10:27
 */

@Data
public class RpcfxRequest {

    private String serviceClass;

    private String method;

    private Object[] params;

}
