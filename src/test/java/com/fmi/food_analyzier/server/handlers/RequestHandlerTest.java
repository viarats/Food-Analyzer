package com.fmi.food_analyzier.server.handlers;

import static com.fmi.food_analyzier.formatter.Formatter.NO_AVAILABLE_INFORMATION_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import com.fmi.food_analyzier.request.RequestData;
import com.fmi.food_analyzier.request.RequestType;
import com.fmi.food_analyzier.request_executor.RequestExecutor;
import com.fmi.food_analyzier.server.handlers.modules.RequestHandlerTestModule;
import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelId;
import io.netty.channel.embedded.EmbeddedChannel;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = RequestHandlerTestModule.class)
public class RequestHandlerTest {
  @Inject private RequestHandler handler;
  @Inject private RequestExecutor executor;

  private RequestHandler handlerSpy;
  private EmbeddedChannel channel;
  private RequestData request;

  @BeforeMethod
  private void setup() {
    handlerSpy = spy(handler);

    channel = new EmbeddedChannel(DefaultChannelId.newInstance());
    channel.pipeline().addLast(handlerSpy);

    request = new RequestData(RequestType.GET_FOOD, UUID.randomUUID().toString());
  }

  @Test
  void testChannelReadSuccessShouldPrintTheResponse() {
    final var response = UUID.randomUUID().toString();
    final var resultFuture = new CompletableFuture<String>();
    resultFuture.complete(response);

    when(executor.executeRequest(request)).thenReturn(resultFuture);
    channel.writeInbound(request);

    sleep500Millis();

    final var actual = channel.readOutbound();
    assertEquals(actual, response);
    verify(executor).executeRequest(request);
  }

  private void sleep500Millis() {
    try {
      Thread.sleep(500);
    } catch (final InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testChannelReadFailureShouldPrintNoAvailableInformationMessage() {
    final var error = new RuntimeException(UUID.randomUUID().toString());
    final var resultFuture = new CompletableFuture<String>();
    resultFuture.completeExceptionally(error);

    when(executor.executeRequest(request)).thenReturn(resultFuture);
    channel.writeInbound(request);

    sleep500Millis();

    final var throwableCaptor = ArgumentCaptor.forClass(Throwable.class);
    verify(handlerSpy).exceptionCaught(any(ChannelHandlerContext.class), throwableCaptor.capture());

    final var throwable = throwableCaptor.getValue();
    assertEquals(throwable, error);

    final var actual = channel.readOutbound();
    assertEquals(actual, NO_AVAILABLE_INFORMATION_MESSAGE);
  }
}
