package com.fmi.food_analyzier.entities.report;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class Nutrient {

  @SerializedName("nutrient")
  private final NutrientData nutrientData;

  @SerializedName("amount")
  private final float value;

  public Nutrient(final NutrientData nutrientData, final float value) {
    this.nutrientData = nutrientData;
    this.value = value;
  }

  public NutrientData getNutrientData() {
    return nutrientData;
  }

  public Float getValue() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Nutrient nutrient = (Nutrient) o;
    return Float.compare(nutrient.value, value) == 0
        && Objects.equals(nutrientData, nutrient.nutrientData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nutrientData, value);
  }
}
