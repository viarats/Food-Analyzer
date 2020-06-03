package com.fmi.food_analyzier.entities.report;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
  @SerializedName("desc")
  private final String description;

  public Ingredient(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
