package com.geek.rpcfx.api;

import lombok.Builder;
import lombok.Data;

/**
 * @author itguoy
 * @date 2021-11-16 10:30
 */

@Data
@Builder
public class ServiceProviderDesc {

    private String host;

    private Integer port;

    private String serviceClass;

}
