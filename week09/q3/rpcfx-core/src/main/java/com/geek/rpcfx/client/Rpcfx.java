package com.geek.rpcfx.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.geek.rpcfx.api.*;
import com.geek.rpcfx.client.netty.NettyHttpClient;
import okhttp3.MediaType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author itguoy
 * @date 2021-11-16 11:05
 */
public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("com.geek");
    }

//    public static <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalancer, Filter filter) {
//        List<String> invokers = new ArrayList<>();
//
//        List<String> urls = router.route(invokers);
//
//        String url = loadBalancer.select(urls);
//
//        return (T) create(serviceClass, url, filter);
//    }

    public static <T> T create(Class<T> serviceClass, String host, int port, String url, Filter... filters) {
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(),
                new Class[]{serviceClass}, new RpcfxInvokerHandler(serviceClass, host, port, url, filters));
    }


    private static class RpcfxInvokerHandler implements InvocationHandler {

        private final Class<?> serviceClass;
        private final String host;
        private final int port;
        private final String url;
        private final Filter[] filters;

        public <T> RpcfxInvokerHandler(Class<T> serviceClass, String host, int port, String url, Filter[] filters) {
            this.serviceClass = serviceClass;
            this.host = host;
            this.port = port;
            this.url = url;
            this.filters = filters;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

            com.geek.rpcfx.api.reflection.RpcfxRequest request = new com.geek.rpcfx.api.reflection.RpcfxRequest();
            request.setServiceClass(this.serviceClass);
            request.setMethod(method.getName());
            request.setParams(params);
//            if (null != filters) {
//                for (Filter filter : filters) {
//                    if (!filter.filter(request)) {
//                        return null;
//                    }
//                }
//            }

//            RpcfxResponse response = post(request, url);
            RpcfxResponse response = nettyPost(request, host, port, url);
            System.out.println(JSON.toJSONString(response));


            return JSON.parse(response.getResult().toString());
        }

        private RpcfxResponse nettyPost(com.geek.rpcfx.api.reflection.RpcfxRequest rpcfxRequest, String host, int port, String url) throws Exception {
            NettyHttpClient client = new NettyHttpClient();
            Map<String, Object> resMap = new ConcurrentHashMap<>();

            client.connect(host, port, url, rpcfxRequest, resMap);
            String respStr = (String) resMap.get("res");
            return JSON.parseObject(respStr, RpcfxResponse.class);
        }


//        private RpcfxResponse post(RpcfxRequest request, String url) throws IOException {
//
//            String reqJson = JSON.toJSONString(request);
//            System.out.println("req json: " + reqJson);
//
//            OkHttpClient client = new OkHttpClient.Builder().build();
//            final Request req = new Request.Builder()
//                    .url(url)
//                    .post(RequestBody.create(JSONTYPE, reqJson))
//                    .build();
//            String respJson = client.newCall(req).execute().body().string();
//            System.out.println("resp json: " + respJson);
//            return JSON.parseObject(respJson, RpcfxResponse.class);
//
//        }
    }
}
