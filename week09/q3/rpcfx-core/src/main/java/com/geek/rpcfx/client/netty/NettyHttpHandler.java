package com.geek.rpcfx.client.netty;

import com.alibaba.fastjson.JSON;
import com.geek.rpcfx.api.RpcfxResponse;
import com.geek.rpcfx.api.reflection.RpcfxRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author itguoy
 * @date 2021-11-17 15:29
 */
public class NettyHttpHandler extends ChannelInboundHandlerAdapter {

    private String url;

    private Map<String, Object> resMap;

    private RpcfxRequest rpcfxRequest;

    public NettyHttpHandler(String url, Map<String, Object> resMap, RpcfxRequest rpcfxRequest) {
        this.url = url;
        this.resMap = resMap;
        this.rpcfxRequest = rpcfxRequest;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            String respStr = response.content().toString(StandardCharsets.UTF_8);
            resMap.put("res", respStr);
            System.out.println("1-->response:  " + resMap);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(NettyHttpClient.buildParams(url, rpcfxRequest)).sync();
        System.out.println("0-->send success");
    }

}
