package com.fmi.food_analyzier.client;

import com.fmi.food_analyzier.client.handlers.RequestEncoder;
import com.fmi.food_analyzier.communicator.Communicator;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

class NettyClient implements Client {
  private final Communicator communicator;
  private final ChannelHandler handler;
  private final String host;
  private final int port;

  @Inject
  NettyClient(
      final Communicator communicator,
      @ClientHandler final ChannelHandler handler,
      @Named("netty.server.host") final String host,
      @Named("netty.server.port") final int port) {
    this.communicator = communicator;
    this.handler = handler;
    this.host = host;
    this.port = port;
  }

  @Override
  public void start() {
    final var group = new NioEventLoopGroup();
    try {
      final var bootstrap = new Bootstrap();
      bootstrap
          .group(group)
          .channel(NioSocketChannel.class)
          .handler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(final SocketChannel ch) {
                  ch.pipeline().addLast(new RequestEncoder(), new StringDecoder(), handler);
                }
              });

      final var channel = bootstrap.connect(host, port).sync().channel();

      communicator.printUsage();
      communicator.printEnterPoint();

      ChannelFuture lastWriteFuture = null;
      while (true) {
        final var request = communicator.readUserRequest();
        if (request == null) {
          break;
        }

        lastWriteFuture = channel.writeAndFlush(request);
      }

      if (lastWriteFuture != null) {
        lastWriteFuture.sync();
      }
    } catch (final Exception e) {
      communicator.printConnectionRefusedMessage();
    } finally {
      group.shutdownGracefully();
    }
  }
}
