package com.geek.rpcfx.demo.provider;

import com.geek.rpcfx.api.RpcfxResponse;
import com.geek.rpcfx.api.ServiceProviderDesc;
import com.geek.rpcfx.api.reflection.RpcfxRequest;
import com.geek.rpcfx.api.reflection.RpcfxResolver;
import com.geek.rpcfx.demo.api.OrderService;
import com.geek.rpcfx.demo.api.UserService;
import com.geek.rpcfx.server.reflection.RpcfxInvoker;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author itguoy
 * @date 2021-11-16 13:51
 */

@SpringBootApplication
@RestController
public class RpcfxServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(RpcfxServerApplication.class, args);
    }


    @Autowired
    RpcfxInvoker invoker;

    @PostMapping("/")
    public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
        System.out.println(request.toString());
        return invoker.invoke(request);
    }

    @GetMapping("/test")
    public String test() {
//        System.out.println(request.toString());
        return "hello, client";
    }

    @Bean
    public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver) {
        return new com.geek.rpcfx.server.reflection.RpcfxInvoker(resolver);
    }

    @Bean
    public RpcfxResolver createResolver() {
//        return new DemoResolver();
        return new RefelectionResolver();
    }


    @Bean(name = "com.geek.rpcfx.demo.api.UserService")
    public UserService createUserService() {
        return new UserServiceImpl();
    }

    @Bean(name = "com.geek.rpcfx.demo.api.OrderService")
    public OrderService createOrderService() {
        return new OrderServiceImpl();
    }


}
