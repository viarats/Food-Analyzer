package com.fmi.food_analyzier.httpclient;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface HttpClient {
  CompletableFuture<HttpResponse> executeGetRequest(String url, Map<String, String> parameters);
}
