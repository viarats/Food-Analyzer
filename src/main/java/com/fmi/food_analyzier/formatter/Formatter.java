package com.fmi.food_analyzier.formatter;

import com.fmi.food_analyzier.entities.Product;
import com.fmi.food_analyzier.entities.report.FoodReport;

public interface Formatter {
  String NO_AVAILABLE_INFORMATION_MESSAGE = "No available information for this product";

  String formatProduct(Product product);

  String formatFoodReport(FoodReport foodReport);
}
