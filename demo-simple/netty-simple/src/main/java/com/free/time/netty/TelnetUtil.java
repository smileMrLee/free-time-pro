package com.free.time.netty;

import com.google.common.base.Throwables;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 利用telnet协议提取dubbo相关参数
 * 
 * @author shiwenxue
 */
@Slf4j
public class TelnetUtil {
    public static List<String> analyzeServiceMethods(String host, int port, String service) {
        List<String> list = new ArrayList<String>();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new TelnetChannelInitializer(service, list));

            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().closeFuture().sync();
        }catch (InterruptedException e){
            log.warn("analyze Service Methods failed, cause:{}", Throwables.getStackTraceAsString(e));
        }
        return list;
    }

    private static class TelnetChannelInitializer extends ChannelInitializer<SocketChannel> {

        /** 要解析的服务接口 */
        private String service;

        /** 解析的结果集 */
        private List<String> result;

        public TelnetChannelInitializer(String service, List<String> result){
            this.service = service;
            this.result = result;
        }

        @Override
        public void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();

            // 以特定标识符解决粘包问题
            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Unpooled.copiedBuffer("dubbo>".getBytes())))
                    .addLast(new StringDecoder())
                    .addLast(new TelnetDecoder())
                    .addLast(new TelnetClientHandler(service, result));
        }
    }

    @ChannelHandler.Sharable
    private static class TelnetClientHandler extends SimpleChannelInboundHandler<String> {

        /** 要解析的服务接口 */
        private String service;

        /** 解析的结果集 */
        private List<String> result;

        public TelnetClientHandler(String service, List<String> result){
            this.service = service;
            this.result = result;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer("ls -l " + service + " \r\n", CharsetUtil.UTF_8));
        }

        /**
         * 读去返回的消息
         */
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            if(msg.equals("end")){
                ctx.channel().close().awaitUninterruptibly();
            }else{
                if(!StringUtils.isEmpty(msg)){
                    result.addAll(Arrays.asList(msg.split("\r\n")));
                }
            }
        }

        /**
         * 异常数据捕获
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.warn("TelnetUtil exception, cause:{}", Throwables.getStackTraceAsString(cause));
            ctx.close();
        }

        protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
            channelRead0(ctx, msg);
        }
    }

    private static class TelnetDecoder extends MessageToMessageDecoder<String>{
        @Override
        protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
            out.add(msg);
            out.add("end");
            ctx.fireChannelRead(out);
        }
    }

}

