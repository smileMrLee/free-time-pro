package com.free.time.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.AsciiString;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author changle
 * time 2019-01-28.
 * 利用netty 编写http服务
 */
@Slf4j
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String TEST_MSG = "hello netty http";

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
       log.info("class:" + msg.getClass().getName());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(TEST_MSG.getBytes()));
        HttpHeaders headers = response.headers();
        headers.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        headers.add(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(response.content().readableBytes()));
        headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channel read complete!");
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("channel error", cause);
        if (null != ctx) ctx.close();
    }
}
