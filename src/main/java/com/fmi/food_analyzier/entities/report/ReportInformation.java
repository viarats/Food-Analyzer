package com.fmi.food_analyzier.entities.report;

public class ReportInformation {
  private final Food food;

  public ReportInformation(final Food food) {
    this.food = food;
  }

  @Override
  public String toString() {
    return food.toString();
  }
}
