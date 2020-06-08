package com.fmi.food_analyzier.request_executor;

import com.fmi.food_analyzier.request.RequestData;
import java.util.concurrent.CompletableFuture;

public interface RequestExecutor {
  CompletableFuture<String> executeRequest(RequestData requestData);
}
