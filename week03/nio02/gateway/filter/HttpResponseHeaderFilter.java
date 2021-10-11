package nio02.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author itguoy
 * @date 2021-10-08 21:42
 */
public class HttpResponseHeaderFilter implements HttpResponseFilter{
    @Override
    public void doFilter(FullHttpResponse response) {
        response.headers().set("hahaha", "mao");
    }
}
