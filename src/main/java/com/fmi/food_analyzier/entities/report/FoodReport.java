package com.fmi.food_analyzier.entities.report;

import com.google.gson.annotations.SerializedName;

public class FoodReport {
  @SerializedName("report")
  private final ReportInformation information;

  public FoodReport(final ReportInformation information) {
    this.information = information;
  }

  public ReportInformation getInformation() {
    return information;
  }
}
