package com.geek.rpcfx.api;

import lombok.Data;

/**
 * @author itguoy
 * @date 2021-11-16 10:29
 */

@Data
public class RpcfxResponse {

    private Object result;

    private boolean status;

    private Exception exception;

}
