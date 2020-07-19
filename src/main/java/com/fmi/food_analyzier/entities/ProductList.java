package com.fmi.food_analyzier.entities;

import com.google.gson.annotations.SerializedName;
import java.util.Set;

public class ProductList {

  @SerializedName("foods")
  private final Set<Item> items;

  public ProductList(final Set<Item> items) {
    this.items = items;
  }

  public Set<Item> getItems() {
    return items;
  }
}
