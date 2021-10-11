package nio02.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author itguoy
 * @date 2021-09-28 9:42
 */
public interface HttpRequestFilter {

    void doFilter(FullHttpRequest request, ChannelHandlerContext ctx);
}
