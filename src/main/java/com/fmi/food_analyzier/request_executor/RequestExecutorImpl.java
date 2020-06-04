package com.fmi.food_analyzier.request_executor;

import com.fmi.food_analyzier.entities.Product;
import com.fmi.food_analyzier.entities.ProductList;
import com.fmi.food_analyzier.entities.report.FoodReport;
import com.fmi.food_analyzier.entities.report.ReportInformation;
import com.fmi.food_analyzier.httpclient.HttpClient;
import com.fmi.food_analyzier.httpclient.HttpResponse;
import com.fmi.food_analyzier.request.RequestData;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class RequestExecutorImpl implements RequestExecutor {
  private static final String GET_FOOD_ENDPOINT = "https://api.nal.usda.gov/ndb/search/";
  private static final String GET_FOOD_REPORT_ENDPOINT = "https://api.nal.usda.gov/ndb/reports/";
  private static final String API_KEY_PARAMETER = "api_key";
  private static final String FOOD_PARAMETER = "q";
  private static final String NDBNO_PARAMETER = "ndbno";
  private static final String TYPE_PARAMETER = "type";
  private static final String TYPE = "b";
  private static final String FORMAT_PARAMETER = "format";
  private static final String JSON = "json";

  private final String apiKey;
  private final HttpClient httpClient;
  private final Gson gson;

  @Inject
  RequestExecutorImpl(
      @Named("api.key") final String apiKey, final HttpClient httpClient, final Gson gson) {
    this.apiKey = apiKey;
    this.httpClient = httpClient;
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
    final var httpResponse = httpClient.executeGetRequest(GET_FOOD_ENDPOINT, parameters);

    return httpResponse.thenApply(
        response -> isStatusValid(response) ? getFood(response) : NO_AVAILABLE_INFORMATION_MESSAGE);
  }

  private boolean isStatusValid(final HttpResponse response) {
    return response.getStatus() == HttpURLConnection.HTTP_OK;
  }

  private String getFood(final HttpResponse response) {
    final var product = gson.fromJson(response.getBody(), Product.class);
    return Optional.ofNullable(product.getList())
        .map(ProductList::toString)
        .orElse(NO_AVAILABLE_INFORMATION_MESSAGE);
  }

  private CompletableFuture<String> processGetFoodReportRequest(final String ndbno) {
    final var parameters =
        Map.of(
            API_KEY_PARAMETER,
            apiKey,
            NDBNO_PARAMETER,
            ndbno,
            TYPE_PARAMETER,
            TYPE,
            FORMAT_PARAMETER,
            JSON);

    final var httpResponse = httpClient.executeGetRequest(GET_FOOD_REPORT_ENDPOINT, parameters);
    return httpResponse.thenApply(
        response ->
            isStatusValid(response) ? getFoodReport(response) : NO_AVAILABLE_INFORMATION_MESSAGE);
  }

  private String getFoodReport(final HttpResponse response) {
    final var report = gson.fromJson(response.getBody(), FoodReport.class);
    return Optional.ofNullable(report.getInformation())
        .map(ReportInformation::toString)
        .orElse(NO_AVAILABLE_INFORMATION_MESSAGE);
  }
}
