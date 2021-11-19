package com.geek.rpcfx.client.netty;

import com.alibaba.fastjson.JSON;
import com.geek.rpcfx.api.RpcfxResponse;
import com.geek.rpcfx.api.reflection.RpcfxRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author itguoy
 * @date 2021-11-17 14:56
 */
public class NettyHttpClient {

    static final String JSONTYPE = "application/json;charset=utf-8";

    public void connect(String host, int port, String url, RpcfxRequest rpcfxRequest, Map<String, Object> resMap) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new HttpRequestEncoder())
                                    .addLast(new HttpResponseDecoder())
                                    .addLast(new HttpObjectAggregator(10 * 1024 * 1024))
                                    .addLast(new NettyHttpHandler(url, resMap, rpcfxRequest));
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


    public static FullHttpRequest buildParams(String url, RpcfxRequest rpcfxRequest) throws URISyntaxException {
        URI uri = new URI(url);
        String msg = JSON.toJSONString(rpcfxRequest);

        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0,
                HttpMethod.POST, uri.toASCIIString(),
                Unpooled.wrappedBuffer(Unpooled.wrappedBuffer(msg.getBytes(StandardCharsets.UTF_8))));


        request.headers()
                .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                .set(HttpHeaderNames.CONTENT_TYPE, JSONTYPE)
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        return request;
    }

}
