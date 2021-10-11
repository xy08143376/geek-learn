package nio02.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author itguoy
 * @date 2021-10-08 21:34
 */
public interface HttpResponseFilter {

    void doFilter(FullHttpResponse response);
}
