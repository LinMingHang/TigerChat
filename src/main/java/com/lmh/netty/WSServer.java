package com.lmh.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * @ClassName: WSServer
 * @Description: TODO
 * @author: ALin
 * @date: 2020/3/29 下午5:53
 */
@Component
public class WSServer {

    private static class SingletionWSServer {
        static final WSServer instance = new WSServer();
    }

    public static WSServer getInstance() {
        return SingletionWSServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public WSServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        // WebSocket 基于http协议,所以要有 http编解码器
                        pipeline.addLast(new HttpServerCodec());
                        // 对写大数据流的支持
                        pipeline.addLast(new ChunkedWriteHandler());
                        // 对 httpMessage 进行聚合,聚合成 FullHttpRequest 或 FullHttpResponse
                        // 几乎在netty中的编程,都会使用到此 handler
                        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

                        // ====================== 以上是用于支持http协议    ======================

                        // ====================== 增加心跳支持 start    ======================
                        // 针对客户端，如果在1分钟时没有向服务端发送读写心跳(ALL)，则主动断开
                        // 如果是读空闲或者写空闲，不处理
                        pipeline.addLast(new IdleStateHandler(8, 10, 12));
                        // 自定义的空闲状态检测
                        pipeline.addLast(new HeartBeatHandler());
                        // ====================== 增加心跳支持 end    ======================

                        // ====================== 以下是支持httpWebSocket ======================

                        /**
                         * WebSocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
                         * 本 handler 会帮你处理一些繁重的复杂的事
                         * 会帮你处理握手动作: handshaking（close, ping, pong） ping + pong = 心跳
                         * 对于 WebSocket 来讲,都是以 frames 进行传输的,不同的数据类型对应的 frames 也不同
                         */
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

                        // 自定义的handler
                        pipeline.addLast(new ChatHandler());
                    }
                });
    }

    public void start() {
        this.future = server.bind(8088);
        System.err.println("netty websocket server 启动完毕...");
    }
}