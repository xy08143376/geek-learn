package com.geek.rpcfx.api;

import java.util.List;

/**
 * @author itguoy
 * @date 2021-11-16 10:28
 */
public interface Router {

    List<String> route(List<String> urls);

}
