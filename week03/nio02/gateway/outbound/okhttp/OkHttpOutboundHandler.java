package nio02.gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import nio02.gateway.filter.HttpRequestFilter;
import nio02.gateway.filter.HttpResponseFilter;
import nio02.gateway.filter.HttpResponseHeaderFilter;
import nio02.gateway.outbound.HttpOutboundHandler;
import nio02.gateway.route.HttpEndpointRouter;
import nio02.gateway.route.RandomHttpEndpointRouter;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author itguoy
 * @date 2021-09-28 9:31
 */
public class OkHttpOutboundHandler implements HttpOutboundHandler {

    private List<String> proxyServers;

    private HttpEndpointRouter router;

    private HttpResponseFilter responseFilter;

    public OkHttpOutboundHandler(List<String> proxyServers) {
        this.proxyServers = proxyServers;
        this.router = new RandomHttpEndpointRouter();
        responseFilter = new HttpResponseHeaderFilter();
    }

    @Override
    public void handler(FullHttpRequest request, ChannelHandlerContext ctx) throws IOException {
        String uri = request.uri();
        String host = router.route(this.proxyServers);
        String url = host + uri;
        Response resp = this.sendReq(url);
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(resp.body().bytes()));
            response.headers().set("Content-Type", "application/json;charset=utf-8");
            response.headers().set("Content-Length", resp.body().contentLength());

            responseFilter.doFilter(response);

        } catch (Exception e) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
            exceptionCaught(ctx, e);

        } finally {
            if (request != null) {
                if (!HttpUtil.isKeepAlive(request)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }


    }

    private Response sendReq(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Response response = client.newCall(request).execute();
        return response;
    }

    private void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
