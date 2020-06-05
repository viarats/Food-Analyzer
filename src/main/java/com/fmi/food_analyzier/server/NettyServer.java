package com.fmi.food_analyzier.server;

import com.fmi.food_analyzier.server.handlers.RequestDecoder;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import java.net.InetSocketAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class NettyServer implements Server {
  private static final Logger LOGGER = LogManager.getLogger();

  private final int port;
  private final int bossThreads;
  private final int workerThreads;
  private final int executorThreads;

  private final ChannelHandler handler;

  private EventLoopGroup bossGroup;
  private EventLoopGroup workerGroup;
  private EventExecutorGroup executorGroup;

  private ChannelFuture channelFuture;

  @Inject
  NettyServer(
      @Named("netty.server.port") final int port,
      @Named("netty.server.boss.threads") final int bossThreads,
      @Named("netty.server.worker.threads") final int workerThreads,
      @Named("netty.server.executor.threads") final int executorThreads,
      @ServerHandler final ChannelHandler handler) {
    this.port = port;
    this.bossThreads = bossThreads;
    this.workerThreads = workerThreads;
    this.executorThreads = executorThreads;
    this.handler = handler;
  }

  public void start() {
    Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

    bossGroup = new NioEventLoopGroup(bossThreads);
    workerGroup = new NioEventLoopGroup(workerThreads);
    executorGroup = new DefaultEventExecutorGroup(executorThreads);

    final var bootstrap = new ServerBootstrap();
    bootstrap
        .group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 128)
        .localAddress(new InetSocketAddress(port))
        .childHandler(
            new ChannelInitializer<SocketChannel>() {
              @Override
              public void initChannel(final SocketChannel ch) {
                ch.pipeline().addLast(new RequestDecoder(), new StringEncoder());
                ch.pipeline().addLast(executorGroup, handler);
              }
            });

    try {
      channelFuture = bootstrap.bind().sync();
      LOGGER.info("Server started on port => {}", port);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void stop() {
    try {
      if (channelFuture != null) {
        channelFuture.channel().close().sync();
      }

      if (bossGroup != null) {
        bossGroup.shutdownGracefully();
      }

      if (workerGroup != null) {
        workerGroup.shutdownGracefully();
      }

      if (executorGroup != null) {
        executorGroup.shutdownGracefully();
      }
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }
}
