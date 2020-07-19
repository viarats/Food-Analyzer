package com.fmi.food_analyzier.entities.report;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class NutrientData {
  private final String name;

  @SerializedName("unitName")
  private final String unit;

  public NutrientData(final String name, final String unit) {
    this.unit = unit;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final NutrientData that = (NutrientData) o;
    return Objects.equals(name, that.name) && Objects.equals(unit, that.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, unit);
  }
}
