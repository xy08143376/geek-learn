package nio02.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nio02.gateway.filter.HttpRequestFilter;
import nio02.gateway.filter.HttpRequestHeaderFilter;
import nio02.gateway.outbound.HttpOutboundHandler;
import nio02.gateway.outbound.okhttp.OkHttpOutboundHandler;

import java.util.List;

/**
 * @author itguoy
 * @date 2021-09-28 9:21
 */
@Data
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private List<String> proxyServers;
    private HttpOutboundHandler outboundHandler;
    private HttpRequestFilter requestFilter;


    public HttpInboundHandler(List<String> proxyServers) {
        this.proxyServers = proxyServers;
        this.outboundHandler = new OkHttpOutboundHandler(proxyServers);
        requestFilter = new HttpRequestHeaderFilter();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 此处去调用真正的后台服务
        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            requestFilter.doFilter(request, ctx);
            if (request.uri().contains("/favicon.ico")) {
                return;
            }

            outboundHandler.handler(request, ctx);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
