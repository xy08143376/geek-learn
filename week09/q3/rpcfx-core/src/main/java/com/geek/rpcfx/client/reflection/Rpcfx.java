package com.geek.rpcfx.client.reflection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.geek.rpcfx.api.LoadBalancer;
import com.geek.rpcfx.api.Router;
import com.geek.rpcfx.api.RpcfxResponse;
import com.geek.rpcfx.api.reflection.Filter;
import com.geek.rpcfx.api.reflection.RpcfxRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author itguoy
 * @date 2021-11-16 15:05
 */
public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("com.geek");
    }

    public static <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl,
                                                    Router router, LoadBalancer loadBalancer,
                                                    Filter filter) {
        List<String> invokers = new ArrayList<>();

        List<String> urls = router.route(invokers);

        String url = loadBalancer.select(urls);

        return (T) create(serviceClass, url, filter);
    }

    public static <T> T create(Class<T> serviceClass, String url, Filter... filters) {
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(),
                new Class[]{serviceClass}, new Rpcfx.rpcfxinvokerHandler(serviceClass, url, filters));

    }


    private static class rpcfxinvokerHandler implements InvocationHandler {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
        private final Class<?> serviceClass;
        private final String url;
        private final Filter[] filters;

        public <T> rpcfxinvokerHandler(Class<T> serviceClass, String url, Filter[] filters) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filters = filters;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass);
            request.setMethod(method.getName());
            request.setParams(params);

//            RpcfxRequest request = new RpcfxRequest();
//            request.setServiceClass(this.serviceClass.getName());
//            request.setMethod(method.getName());
//            request.setParams(params);

            if (null != filters) {
                for (Filter filter : filters) {
                    if (!filter.filter(request)) {
                        return null;
                    }
                }
            }

            RpcfxResponse response = post(request, url);


            return JSON.parse(response.getResult().toString());
        }

        private RpcfxResponse post(RpcfxRequest request, String url) throws IOException {

            String reqJson = JSON.toJSONString(request);
            System.out.println("req json: " + reqJson);

            OkHttpClient client = new OkHttpClient.Builder().build();
            final Request req = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(req).execute().body().string();
            System.out.println("resp json: " + respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);

        }
    }

}
