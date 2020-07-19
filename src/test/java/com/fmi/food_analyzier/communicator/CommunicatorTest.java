package com.fmi.food_analyzier.communicator;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

import com.fmi.food_analyzier.communicator.modules.CommunicatorTestModule;
import com.fmi.food_analyzier.reader.Reader;
import com.fmi.food_analyzier.request.RequestData;
import com.fmi.food_analyzier.request.RequestType;
import com.fmi.food_analyzier.writer.Writer;
import com.google.inject.Inject;
import java.util.UUID;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = CommunicatorTestModule.class)
public class CommunicatorTest {
  private static final String USAGE =
      "Supported requests are:\n get-food <food_name>\n get-food-report <ndbno>"
          + "\nThe ndbno of a product can be retrieved from the \"get-food\" command";
  private static final String ENTER_POINT = "> ";
  private static final String UNKNOWN_COMMAND = "Unknown command => %s";
  private static final String CONNECTION_REFUSED =
      "Could not connect to server. Please, try again later";
  private static final String GET_FOOD = "get-food ";
  private static final String GET_FOOD_REPORT = "get-food-report ";

  @Inject private Communicator communicator;
  @Inject private Reader reader;
  @Inject private Writer writer;

  @BeforeMethod
  private void setup() {
    initMocks(this);
  }

  @Test
  void testPrintUsage() {
    communicator.printUsage();
    verify(writer).writeLine(USAGE);
  }

  @Test
  void testPrintEnterPoint() {
    communicator.printEnterPoint();
    verify(writer).write(ENTER_POINT);
  }

  @Test
  void testPrintConnectionRefusedMessage() {
    communicator.printConnectionRefusedMessage();
    verify(writer).writeLine(CONNECTION_REFUSED);
  }

  @Test
  void testPrintRequestResult() {
    final var result = UUID.randomUUID().toString();

    communicator.printRequestResult(result);
    verify(writer).writeLine(result);
  }

  @DataProvider(name = "data")
  private Object[][] provideData() {
    final var food = UUID.randomUUID().toString();
    final var upc = UUID.randomUUID().toString();
    return new Object[][] {
      {GET_FOOD + food, new RequestData(RequestType.GET_FOOD, food)},
      {GET_FOOD_REPORT + upc, new RequestData(RequestType.GET_FOOD_REPORT, upc)}
    };
  }

  @Test(dataProvider = "data")
  void testReadUserRequestSuccess(final String request, final RequestData expected) {
    when(reader.read()).thenReturn(request);

    final var actual = communicator.readUserRequest();
    assertEquals(actual, expected);
  }

  @Test
  void testReadUserRequestFailureShouldPrintUnknownCommandMessage() {
    final var invalidRequest = UUID.randomUUID().toString();
    final var food = UUID.randomUUID().toString();
    final var validRequest = GET_FOOD + food;

    when(reader.read()).thenReturn(invalidRequest, invalidRequest, invalidRequest, validRequest);

    final var actual = communicator.readUserRequest();

    final var expectedResult = new RequestData(RequestType.GET_FOOD, food);
    assertEquals(actual, expectedResult);

    final int expectedInvocationTimes = 3;
    verify(writer, times(expectedInvocationTimes))
        .writeLine(String.format(UNKNOWN_COMMAND, invalidRequest));
  }
}
