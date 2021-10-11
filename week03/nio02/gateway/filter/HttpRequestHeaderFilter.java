package nio02.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author itguoy
 * @date 2021-10-08 21:41
 */
public class HttpRequestHeaderFilter implements HttpRequestFilter{
    @Override
    public void doFilter(FullHttpRequest request, ChannelHandlerContext ctx) {
        request.headers().set("mao", "hahaha");
    }
}
