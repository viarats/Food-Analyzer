package com.fmi.food_analyzier.request_executor;

import static com.fmi.food_analyzier.formatter.Formatter.NO_AVAILABLE_INFORMATION_MESSAGE;
import static java.net.HttpURLConnection.HTTP_OK;

import com.fmi.food_analyzier.entities.ProductList;
import com.fmi.food_analyzier.entities.report.Food;
import com.fmi.food_analyzier.formatter.Formatter;
import com.fmi.food_analyzier.httpclient.HttpClient;
import com.fmi.food_analyzier.httpclient.HttpResponse;
import com.fmi.food_analyzier.request.RequestData;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestExecutorImpl implements RequestExecutor {
  private static final Logger LOGGER = LogManager.getLogger();

  private static final String API_KEY_PARAMETER = "api_key";
  private static final String FOOD_PARAMETER = "query";

  private final HttpClient httpClient;
  private final Gson gson;
  private final Formatter formatter;

  private final String apiKey;
  private final String getFoodEndpoint;
  private final String getFoodReportEndpoint;

  @Inject
  RequestExecutorImpl(
      @Named("api.key") final String apiKey,
      @Named("get.food.endpoint") final String getFoodEndpoint,
      @Named("get.food.report.endpoint") final String getFoodReportEndpoint,
      final HttpClient httpClient,
      final Formatter formatter,
      final Gson gson) {
    this.apiKey = apiKey;
    this.getFoodEndpoint = getFoodEndpoint;
    this.getFoodReportEndpoint = getFoodReportEndpoint;
    this.httpClient = httpClient;
    this.formatter = formatter;
    this.gson = gson;
  }

  @Override
  public CompletableFuture<String> executeRequest(final RequestData requestData) {
    switch (requestData.getRequestType()) {
      case GET_FOOD:
        return processGetFoodRequest(requestData.getParameter());
      case GET_FOOD_REPORT:
        return processGetFoodReportRequest(requestData.getParameter());
      default:
        throw new RuntimeException(
            String.format("Unknown command: %s", requestData.getRequestType()));
    }
  }

  private CompletableFuture<String> processGetFoodRequest(final String food) {
    final var parameters = Map.of(API_KEY_PARAMETER, apiKey, FOOD_PARAMETER, food);
    final var httpResponse = httpClient.executeGetRequest(getFoodEndpoint, parameters);

    return httpResponse.thenApply(
        response -> isStatusValid(response) ? getFood(response) : NO_AVAILABLE_INFORMATION_MESSAGE);
  }

  private boolean isStatusValid(final HttpResponse response) {
    final int status = response.getStatus();
    LOGGER.info("HTTP status code = {}", status);

    return status == HTTP_OK;
  }

  private String getFood(final HttpResponse response) {
    final var productList = gson.fromJson(response.getBody(), ProductList.class);
    return formatter.formatProductList(productList);
  }

  private CompletableFuture<String> processGetFoodReportRequest(final String ndbno) {
    final var parameters = Map.of(API_KEY_PARAMETER, apiKey);
    final var url = getFoodReportEndpoint + ndbno;

    final var httpResponse = httpClient.executeGetRequest(url, parameters);
    return httpResponse.thenApply(
        response ->
            isStatusValid(response) ? getFoodReport(response) : NO_AVAILABLE_INFORMATION_MESSAGE);
  }

  private String getFoodReport(final HttpResponse response) {
    final var report = gson.fromJson(response.getBody(), Food.class);
    return formatter.formatFoodReport(report);
  }
}
