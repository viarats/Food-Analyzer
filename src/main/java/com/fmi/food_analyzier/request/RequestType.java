package com.fmi.food_analyzier.request;

import java.util.Arrays;

public enum RequestType {
  GET_FOOD("get-food"),
  GET_FOOD_REPORT("get-food-report");

  private final String name;

  RequestType(final String name) {
    this.name = name;
  }

  public static RequestType getByName(final String name) {
    return Arrays.stream(RequestType.values())
        .filter(type -> type.getName().equals(name))
        .findAny()
        .orElseThrow(() -> new RuntimeException(String.format("Unknown request type => %s", name)));
  }

  public String getName() {
    return name;
  }
}
