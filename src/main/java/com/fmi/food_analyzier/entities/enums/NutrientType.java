package com.fmi.food_analyzier.entities.enums;

public enum NutrientType {
  ENERGY("Energy"),
  PROTEIN("Protein"),
  FAT("Total lipid (fat)"),
  CARBOHYDRATE("Carbohydrate, by difference"),
  FIBER("Fiber, total dietary");

  private final String value;

  NutrientType(final String value) {
    this.value = value;
  }

  public String getName() {
    return value;
  }
}
