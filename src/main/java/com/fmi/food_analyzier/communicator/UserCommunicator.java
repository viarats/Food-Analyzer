package com.fmi.food_analyzier.communicator;

import com.fmi.food_analyzier.reader.Reader;
import com.fmi.food_analyzier.request.RequestData;
import com.fmi.food_analyzier.request.RequestType;
import com.fmi.food_analyzier.writer.Writer;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.stream.Collectors;

class UserCommunicator implements Communicator {
  private static final String USAGE =
      "Supported requests are:\n get-food <food_name>\n get-food-report <ndbno>"
          + "\nThe ndbno of a product can be retrieved from the \"get-food\" command";
  private static final String ENTER_POINT = "> ";
  private static final String UNKNOWN_COMMAND = "Unknown command => %s";
  private static final String CONNECTION_REFUSED =
      "Could not connect to server. Please, try again later";
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
    writer.writeLine(USAGE);
  }

  @Override
  public void printEnterPoint() {
    writer.write(ENTER_POINT);
  }

  @Override
  public void printConnectionRefusedMessage() {
    writer.writeLine(CONNECTION_REFUSED);
  }

  @Override
  public void printRequestResult(final String result) {
    writer.writeLine(result);
  }

  @Override
  public RequestData readUserRequest() {
    var input = reader.read();
    var words = input.split(REGEX);

    RequestType requestType;
    while (words.length <= 0 || (requestType = getRequestType(words[0])) == null) {
      printEnterPoint();

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

  private void printUnknownCommandMessage(final String command) {
    writer.writeLine(String.format(UNKNOWN_COMMAND, command));
  }

  private String getFoodName(final String[] words) {
    return Arrays.stream(words).skip(1).collect(Collectors.joining(DELIMITER));
  }
}
