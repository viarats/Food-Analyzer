package com.fmi.food_analyzier.server.handlers;

import static com.fmi.food_analyzier.formatter.Formatter.NO_AVAILABLE_INFORMATION_MESSAGE;

import com.fmi.food_analyzier.request.RequestData;
import com.fmi.food_analyzier.request_executor.RequestExecutor;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Optional;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Sharable
public class RequestHandler extends ChannelInboundHandlerAdapter {
  private static Logger LOGGER = LogManager.getLogger();
  private final RequestExecutor requestExecutor;

  @Inject
  RequestHandler(final RequestExecutor requestExecutor) {
    this.requestExecutor = requestExecutor;
  }

  @Override
  public void channelRead(final ChannelHandlerContext context, final Object message) {
    final var request = (RequestData) message;
    requestExecutor
        .executeRequest(request)
        .whenCompleteAsync(
            (response, error) ->
                Optional.ofNullable(error)
                    .ifPresentOrElse(
                        e -> exceptionCaught(context, e),
                        () -> writeSuccessfulResponse(context, response)));
  }

  private void writeSuccessfulResponse(final ChannelHandlerContext context, final String response) {
    context.writeAndFlush(response);
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
    context.writeAndFlush(NO_AVAILABLE_INFORMATION_MESSAGE);
    LOGGER.error("Exception caught => {}", cause.getMessage());

    if (!context.channel().isOpen()) {
      LOGGER.info("Client with channel id {} left.", context.channel().id());
      context.close();
    }
  }
}
