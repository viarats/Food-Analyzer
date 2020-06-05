package com.fmi.food_analyzier.request;

import java.util.Objects;

public class RequestData {
  private final RequestType requestType;
  private final String parameter;

  public RequestData(final RequestType requestType, final String parameter) {
    this.requestType = requestType;
    this.parameter = parameter;
  }

  public RequestType getRequestType() {
    return requestType;
  }

  public String getParameter() {
    return parameter;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final RequestData that = (RequestData) o;
    return requestType == that.requestType && Objects.equals(parameter, that.parameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestType, parameter);
  }
}
