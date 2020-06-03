package com.fmi.food_analyzier.entities.report;

import java.util.Objects;

public class Nutrient {
  private final String name;
  private final String unit;
  private final String value;

  public Nutrient(final String name, final String unit, final String value) {
    this.name = name;
    this.unit = unit;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  public String getValue() {
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
    return Objects.equals(name, nutrient.name)
        && Objects.equals(unit, nutrient.unit)
        && Objects.equals(value, nutrient.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, unit, value);
  }
}
