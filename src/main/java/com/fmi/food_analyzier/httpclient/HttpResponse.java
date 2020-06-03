package com.fmi.food_analyzier.httpclient;

public class HttpResponse {
  private final String body;
  private final int status;

  HttpResponse(final String body, final int status) {
    this.body = body;
    this.status = status;
  }

  public String getBody() {
    return body;
  }

  public int getStatus() {
    return status;
  }
}
