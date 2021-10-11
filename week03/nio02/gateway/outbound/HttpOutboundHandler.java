package nio02.gateway.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import nio02.gateway.filter.HttpRequestFilter;

import java.io.IOException;

/**
 * @author itguoy
 * @date 2021-09-28 9:32
 */
public interface HttpOutboundHandler {

    void handler(FullHttpRequest request, ChannelHandlerContext ctx) throws IOException;
}
