package com.fmi.food_analyzier.client.handlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

import com.fmi.food_analyzier.client.handlers.modules.ResponseHandlerTestModule;
import com.fmi.food_analyzier.communicator.Communicator;
import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelId;
import io.netty.channel.embedded.EmbeddedChannel;
import java.util.UUID;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = ResponseHandlerTestModule.class)
public class ResponseHandlerTest {
  @Inject private ResponseHandler handler;
  @Inject private Communicator communicator;

  private ResponseHandler handlerSpy;
  private EmbeddedChannel channel;

  @BeforeMethod
  private void setup() {
    handlerSpy = spy(handler);

    channel = new EmbeddedChannel(DefaultChannelId.newInstance());
    channel.pipeline().addLast(handlerSpy);
  }

  @Test
  void testChannelRead0SuccessShouldPrintTheResult() {
    final var result = UUID.randomUUID().toString();
    channel.writeInbound(result);

    verify(communicator).printRequestResult(result);
    verify(communicator).printEnterPoint();
  }

  @Test
  void testChannelRead0FailureShouldPrintConnectionRefusedMessage() {
    final var result = UUID.randomUUID().toString();

    final var errorMessage = UUID.randomUUID().toString();
    doThrow(new RuntimeException(errorMessage)).when(communicator).printRequestResult(result);

    channel.writeInbound(result);

    final var captor = ArgumentCaptor.forClass(Throwable.class);
    verify(handlerSpy).exceptionCaught(any(ChannelHandlerContext.class), captor.capture());

    final var exception = captor.getValue();

    assertEquals(exception.getClass(), RuntimeException.class);
    assertEquals(exception.getMessage(), errorMessage);
    verify(communicator).printConnectionRefusedMessage();
  }
}
