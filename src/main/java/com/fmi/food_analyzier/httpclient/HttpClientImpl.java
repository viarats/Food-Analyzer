package com.fmi.food_analyzier.httpclient;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.netty.handler.codec.http.QueryStringEncoder;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class HttpClientImpl implements HttpClient {
  private final java.net.http.HttpClient httpClient;

  private final int readTimeout;

  @Inject
  HttpClientImpl(
      @Named("http.client.connect.timeout") final int connectTimeout,
      @Named("http.client.read.timeout") final int readTimeout) {
    httpClient =
        java.net.http.HttpClient.newBuilder()
            .connectTimeout(Duration.ofMillis(connectTimeout))
            .version(java.net.http.HttpClient.Version.HTTP_1_1)
            .build();
    this.readTimeout = readTimeout;
  }

  @Override
  public CompletableFuture<HttpResponse> executeGetRequest(
      final String url, final Map<String, String> parameters) {
    final var queryEncoder = new QueryStringEncoder(url);
    parameters.forEach(queryEncoder::addParam);

    try {
      final var builder =
          HttpRequest.newBuilder(queryEncoder.toUri()).timeout(Duration.ofMillis(readTimeout));
      return httpClient
          .sendAsync(builder.build(), java.net.http.HttpResponse.BodyHandlers.ofString())
          .thenApply(response -> new HttpResponse(response.body(), response.statusCode()));
    } catch (final URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
