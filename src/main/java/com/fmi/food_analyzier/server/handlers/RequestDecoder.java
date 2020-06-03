package com.fmi.food_analyzier.server.handlers;

import com.fmi.food_analyzier.request.RequestData;
import com.fmi.food_analyzier.request.RequestType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestDecoder extends ReplayingDecoder<RequestData> {

  @Override
  protected void decode(
      final ChannelHandlerContext context, final ByteBuf in, final List<Object> out) {
    final int typeOrdinal = in.readInt();
    final int length = in.readInt();
    final var parameter = in.readCharSequence(length, StandardCharsets.UTF_8).toString();

    final var data = new RequestData(RequestType.values()[typeOrdinal], parameter);
    out.add(data);
  }
}
