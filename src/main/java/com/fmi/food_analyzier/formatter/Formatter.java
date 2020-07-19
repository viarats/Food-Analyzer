package com.fmi.food_analyzier.formatter;

import com.fmi.food_analyzier.entities.ProductList;
import com.fmi.food_analyzier.entities.report.Food;

public interface Formatter {
  String NO_AVAILABLE_INFORMATION_MESSAGE = "No available information for this product";

  String formatProductList(ProductList product);

  String formatFoodReport(Food foodReport);
}
