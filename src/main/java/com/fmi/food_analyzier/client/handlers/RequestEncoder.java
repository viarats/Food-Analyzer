package com.fmi.food_analyzier.client.handlers;

import com.fmi.food_analyzier.request.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.StandardCharsets;

public class RequestEncoder extends MessageToByteEncoder<RequestData> {

  @Override
  protected void encode(
      final ChannelHandlerContext context, final RequestData request, final ByteBuf out) {
    out.writeInt(request.getRequestType().ordinal());
    out.writeInt(request.getParameter().length());
    out.writeCharSequence(request.getParameter(), StandardCharsets.UTF_8);
  }
}
