package com.free.time.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author changle
 * time 2019-01-28.
 * 服务启动类
 */
@Slf4j
public class HttpServer {
    private final int port;

    public HttpServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new HttpServer(8898).start();
    }

    public void start() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception{
                        log.info("init channel!");
                        ch.pipeline().addLast("decoder", new HttpRequestDecoder())
                                .addLast("encoder", new HttpRequestEncoder())
                                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                                .addLast("handler", new HttpHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        bootstrap.bind(port).sync();
    }
}
