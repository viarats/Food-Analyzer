package com.fmi.food_analyzier.request_executor;

import com.fmi.food_analyzier.request.RequestData;
import java.util.concurrent.CompletableFuture;

public interface RequestExecutor {
  String NO_AVAILABLE_INFORMATION_MESSAGE = "No available information for this product";

  CompletableFuture<String> executeRequest(RequestData requestData);
}
