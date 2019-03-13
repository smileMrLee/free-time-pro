package com.free.time.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author changle
 * time 2019-03-13.
 * description to do
 */
public class WebsocketServer {
    public static void main(String[] args) throws Exception {
        int port = 8090;
        new WebsocketServer().run(port);
    }

    public void run(int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("http-codec", new HttpServerCodec())
                            .addLast("aggregator", new HttpObjectAggregator(65536))
                            .addLast("http-chunked", new ChunkedWriteHandler())
                            .addLast("handler", new WebSocketServerHandler());
                }
            });

            Channel ch = bootstrap.bind(port).sync().channel();
            System.out.println("web socket server started at port " + port + ".");
            System.out.println("open your browser and navigate to http://loaclhost:" + port + "/");
            ch.closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
