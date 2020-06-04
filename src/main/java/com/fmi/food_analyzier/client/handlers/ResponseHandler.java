package com.fmi.food_analyzier.client.handlers;

import com.fmi.food_analyzier.communicator.Communicator;
import com.google.inject.Inject;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class ResponseHandler extends SimpleChannelInboundHandler<String> {
  private final Communicator communicator;

  @Inject
  ResponseHandler(final Communicator communicator) {
    this.communicator = communicator;
  }

  @Override
  public void channelRead0(final ChannelHandlerContext context, final String message) {
    communicator.printRequestResult(message);
    communicator.printEnterPoint();
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
    communicator.printConnectionRefusedMessage();
    context.close();
  }
}
