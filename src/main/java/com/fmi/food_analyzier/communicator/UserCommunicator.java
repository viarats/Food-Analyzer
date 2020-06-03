package com.fmi.food_analyzier.communicator;

import com.fmi.food_analyzier.communicator.reader.Reader;
import com.fmi.food_analyzier.communicator.writer.Writer;
import com.fmi.food_analyzier.request.RequestData;
import com.fmi.food_analyzier.request.RequestType;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.stream.Collectors;

class UserCommunicator implements Communicator {
  private static final String USAGE = "Unknown command";
  private static final String UNKNOWN_COMMAND = "Unknown command";
  private static final String REGEX = "\\s+";
  private static final String DELIMITER = " ";

  private final Reader reader;
  private final Writer writer;

  @Inject
  UserCommunicator(final Reader reader, final Writer writer) {
    this.reader = reader;
    this.writer = writer;
  }

  @Override
  public void printUsage() {
    writer.write(USAGE);
  }

  @Override
  public void printUnknownCommandMessage(final String command) {
    writer.write(UNKNOWN_COMMAND);
  }

  @Override
  public void printRequestResult(final String result) {
    writer.write(result);
  }

  @Override
  public RequestData readUserRequest() {
    var input = reader.read();
    var words = input.split(REGEX);

    RequestType requestType;
    while ((requestType = getRequestType(words[0])) == null) {
      input = reader.read();
      words = input.split(REGEX);
    }

    return new RequestData(requestType, getFoodName(words));
  }

  private RequestType getRequestType(final String command) {
    try {
      return RequestType.getByName(command);
    } catch (final RuntimeException e) {
      printUnknownCommandMessage(command);
      return null;
    }
  }

  private String getFoodName(final String[] words) {
    return Arrays.stream(words).skip(1).collect(Collectors.joining(DELIMITER));
  }
}
