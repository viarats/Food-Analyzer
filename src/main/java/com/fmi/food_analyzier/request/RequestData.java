package com.fmi.food_analyzier.request;

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
}
