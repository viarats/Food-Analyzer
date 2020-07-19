package com.fmi.food_analyzier.request_executor;

import static com.fmi.food_analyzier.formatter.Formatter.NO_AVAILABLE_INFORMATION_MESSAGE;
import static com.fmi.food_analyzier.request_executor.utils.RequestExecutorUtils.generateRandomProductList;
import static com.fmi.food_analyzier.request_executor.utils.RequestExecutorUtils.generateRandomReport;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.fmi.food_analyzier.entities.ProductList;
import com.fmi.food_analyzier.entities.report.Food;
import com.fmi.food_analyzier.formatter.Formatter;
import com.fmi.food_analyzier.httpclient.HttpClient;
import com.fmi.food_analyzier.httpclient.HttpResponse;
import com.fmi.food_analyzier.request.RequestData;
import com.fmi.food_analyzier.request.RequestType;
import com.fmi.food_analyzier.request_executor.modules.TestModule;
import com.google.gson.Gson;
import com.google.inject.Inject;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = TestModule.class)
public class RequestExecutorTest {

  @Inject private RequestExecutor executor;
  @Inject private HttpClient httpClient;
  @Inject private Formatter formatter;
  @Inject private Gson gson;

  private CompletableFuture<HttpResponse> future;

  @BeforeMethod
  private void setup() {
    future = new CompletableFuture<>();
    when(httpClient.executeGetRequest(anyString(), any())).thenReturn(future);
  }

  @DataProvider(name = "foodData")
  private Object[][] provideFoodData() {
    final var productList = generateRandomProductList();
    return new Object[][] {
      {productList, HTTP_OK, formatter.formatProductList(productList)},
      {new ProductList(Set.of()), HTTP_OK, NO_AVAILABLE_INFORMATION_MESSAGE},
      {productList, HTTP_INTERNAL_ERROR, NO_AVAILABLE_INFORMATION_MESSAGE}
    };
  }

  @Test(dataProvider = "foodData")
  void testGetFoodRequest(final ProductList productList, final int status, final String expected) {
    final var response = new HttpResponse(gson.toJson(productList), status);
    future.complete(response);

    final var request = new RequestData(RequestType.GET_FOOD, UUID.randomUUID().toString());
    final var actual = executor.executeRequest(request);

    assertTrue(actual.isDone());
    assertEquals(actual.join(), expected);
  }

  @DataProvider(name = "foodReportData")
  private Object[][] provideFoodReportData() {
    final var foodReport = generateRandomReport();
    return new Object[][] {
      {foodReport, HTTP_OK, formatter.formatFoodReport(foodReport)},
      {foodReport, HTTP_INTERNAL_ERROR, NO_AVAILABLE_INFORMATION_MESSAGE}
    };
  }

  @Test(dataProvider = "foodReportData")
  void testGetFoodReportRequest(final Food foodReport, final int status, final String expected) {
    final var response = new HttpResponse(gson.toJson(foodReport), status);
    future.complete(response);

    final var request = new RequestData(RequestType.GET_FOOD_REPORT, UUID.randomUUID().toString());
    final var actual = executor.executeRequest(request);

    assertTrue(actual.isDone());
    assertEquals(actual.join(), expected);
  }
}
