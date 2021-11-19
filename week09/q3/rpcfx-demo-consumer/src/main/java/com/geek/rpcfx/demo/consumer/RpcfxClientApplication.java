package com.geek.rpcfx.demo.consumer;

import com.alibaba.fastjson.JSON;
import com.geek.rpcfx.api.RpcfxResponse;
import com.geek.rpcfx.client.Rpcfx;
import com.geek.rpcfx.client.netty.NettyHttpClient;
import com.geek.rpcfx.demo.api.User;
import com.geek.rpcfx.demo.api.UserService;
import com.geek.rpcfx.demo.consumer.aop.BeforeAdvice;
import com.geek.rpcfx.demo.consumer.aop.ProxyFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author itguoy
 * @date 2021-11-16 14:04
 */
@SpringBootApplication
public class RpcfxClientApplication {


    public static void main(String[] args) {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setServiceClass(UserService.class);
        proxyFactory.setBeforeAdvice(new BeforeAdvice() {
            @Override
            public Object before(Class serviceClass) throws Exception {
                System.out.println("->before advice");

                com.geek.rpcfx.api.reflection.RpcfxRequest rpcfxRequest = new com.geek.rpcfx.api.reflection.RpcfxRequest();
                rpcfxRequest.setServiceClass(serviceClass);
                rpcfxRequest.setMethod("findById");
                rpcfxRequest.setParams(new Object[]{1});

                NettyHttpClient client = new NettyHttpClient();
                Map<String, Object> resMap = new ConcurrentHashMap<>();

                client.connect("127.0.0.1", 8080, "/", rpcfxRequest, resMap);
                String respStr = (String) resMap.get("res");
                System.out.println("333--"+respStr);
                return JSON.parseObject(respStr, RpcfxResponse.class);
            }
        });

        UserService userService1 = (UserService) proxyFactory.createProxy();

        User user1 = userService1.findById(1);
        System.out.println("aop find user id=1 from server: " + user1.getName());

//        UserService userService = Rpcfx.create(UserService.class, "127.0.0.1", 8080, "/");
//        System.out.println("class-->" + userService);
//        User user = userService.findById(1);
//        System.out.println("find user id=1 from server: " + user.getName());

//        SpringApplication.run(RpcfxClientApplication.class, args);

    }


//    private static class TagRouter implements Router {
//
//        @Override
//        public List<String> route(List<String> urls) {
//            return urls;
//        }
//    }
//
//    private static class RandomLoadBalancer implements LoadBalancer {
//        @Override
//        public String select(List<String> urls) {
//            return urls.get(0);
//        }
//    }
//
//    @Slf4j
//    private static class GeekFilter implements Filter {
//        @Override
//        public boolean filter(RpcfxRequest request) {
//            log.info("filter {} -> {}", this.getClass().getName(), request.toString());
//            return true;
//        }
//    }


}
