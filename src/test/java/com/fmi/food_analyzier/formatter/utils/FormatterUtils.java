package com.fmi.food_analyzier.formatter.utils;

import com.fmi.food_analyzier.entities.Item;
import com.fmi.food_analyzier.entities.enums.NutrientType;
import com.fmi.food_analyzier.entities.report.Food;
import com.fmi.food_analyzier.entities.report.Nutrient;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FormatterUtils {
  private static final String NEW_LINE = "\n";
  private static final int LOWER_BOUND = 1;
  private static final int UPPER_BOUND = 5;

  public static Set<Item> generateItems(final String group, final String name) {
    final int size = ThreadLocalRandom.current().nextInt(LOWER_BOUND, UPPER_BOUND);
    return Stream.generate(
            () -> new Item(name, UUID.randomUUID().toString(), group, UUID.randomUUID().toString()))
        .limit(size)
        .collect(Collectors.toUnmodifiableSet());
  }

  public static String formatProduct(final String name, final String ndbno) {
    return "Name: " + name + ", NDBNO: " + ndbno;
  }

  public static String formatBrandedProduct(
      final String name, final String ndbno, final String upc) {
    return "Name: " + name + ", NDBNO: " + ndbno + ", UPC: " + upc;
  }

  public static String formatReport(final Food foodReport) {
    return "Name: "
        + foodReport.getName()
        + NEW_LINE
        + "Ingredients: "
        + foodReport.getIngredients()
        + NEW_LINE
        + formatNutrients(foodReport.getNutrients());
  }

  private static String formatNutrients(final Set<Nutrient> nutrients) {
    return Arrays.stream(NutrientType.values())
        .map(
            type ->
                nutrients.stream()
                        .filter(
                            nutrient ->
                                nutrient.getNutrientData().getName().endsWith(type.getValue()))
                        .map(
                            nutrient ->
                                nutrient.getNutrientData().getName()
                                    + ": "
                                    + nutrient.getValue()
                                    + nutrient.getNutrientData().getUnit()
                                    + "/100g")
                        .collect(Collectors.joining())
                    + NEW_LINE)
        .collect(Collectors.joining());
  }
}
