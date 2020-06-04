package com.fmi.food_analyzier.entities;

import static java.util.stream.Collectors.joining;

import com.google.gson.annotations.SerializedName;
import java.util.Set;

public class ProductList {
  private static final String DELIMITER = "\n";

  @SerializedName("item")
  private final Set<Item> items;

  public ProductList(final Set<Item> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return items.stream().map(Item::toString).collect(joining(DELIMITER)) + DELIMITER;
  }
}
