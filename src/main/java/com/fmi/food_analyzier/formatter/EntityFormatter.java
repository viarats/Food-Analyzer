package com.fmi.food_analyzier.formatter;

import static java.util.stream.Collectors.joining;

import com.fmi.food_analyzier.entities.Item;
import com.fmi.food_analyzier.entities.Product;
import com.fmi.food_analyzier.entities.ProductList;
import com.fmi.food_analyzier.entities.enums.NutrientType;
import com.fmi.food_analyzier.entities.report.FoodReport;
import com.fmi.food_analyzier.entities.report.Nutrient;
import com.fmi.food_analyzier.entities.report.ReportInformation;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntityFormatter implements Formatter {
  private static final String BRANDED_FOODS = "Branded Food Products Database";
  private static final String NAME = "Name: ";
  private static final String NDBNO = " NDBNO: ";
  private static final String UPC = " UPC: ";
  private static final String INGREDIENTS = "Ingredients: ";
  private static final String PER_GRAMS = "/100g";

  private static final String DELIMITER = ": ";
  private static final String NEW_LINE = "\n";
  private static final String WHITESPACE = " ";
  private static final String REGEX = "\\s+";
  private static final String COMMA = ",";

  @Override
  public String formatProduct(final Product product) {
    return Optional.ofNullable(product.getList())
        .map(this::formatProductList)
        .orElse(NO_AVAILABLE_INFORMATION_MESSAGE);
  }

  private String formatProductList(final ProductList productList) {
    return productList.getItems().stream().map(this::formatItem).collect(joining(NEW_LINE))
        + NEW_LINE;
  }

  private String formatItem(final Item item) {
    final var builder = new StringBuilder(NAME);
    final var ndbno = item.getNdbno();

    if (item.getGroup().equals(BRANDED_FOODS)) {
      final String[] tokens = item.getName().split(REGEX);
      final var upc = tokens[tokens.length - 1];
      final var name = extractName(tokens);

      builder
          .append(name)
          .append(COMMA)
          .append(NDBNO)
          .append(ndbno)
          .append(COMMA)
          .append(UPC)
          .append(upc);
    } else {
      builder.append(item.getName()).append(COMMA).append(NDBNO).append(ndbno);
    }

    return builder.toString();
  }

  private String extractName(final String[] tokens) {
    return IntStream.range(0, tokens.length - 2)
        .boxed()
        .map(index -> tokens[index])
        .collect(Collectors.joining(WHITESPACE));
  }

  @Override
  public String formatFoodReport(final FoodReport foodReport) {
    return Optional.ofNullable(foodReport.getInformation())
        .map(this::formatReportInformation)
        .orElse(NO_AVAILABLE_INFORMATION_MESSAGE);
  }

  private String formatReportInformation(final ReportInformation information) {
    final var food = information.getFood();
    final StringBuilder builder = new StringBuilder(NAME);
    builder.append(food.getName());

    Optional.ofNullable(food.getIngredients())
        .ifPresent(
            ingredients ->
                builder.append(NEW_LINE).append(INGREDIENTS).append(ingredients.getDescription()));

    builder.append(NEW_LINE);
    Arrays.stream(NutrientType.values())
        .forEach(type -> appendNutrient(builder, food.getNutrients(), type));

    return builder.toString();
  }

  private void appendNutrient(
      final StringBuilder stringBuilder, final Set<Nutrient> nutrients, final NutrientType type) {
    stringBuilder
        .append(
            nutrients.stream()
                .filter(nutrient -> nutrient.getName().endsWith(type.getValue()))
                .map(this::formatNutrient)
                .collect(Collectors.joining()))
        .append(NEW_LINE);
  }

  private String formatNutrient(final Nutrient nutrient) {
    return nutrient.getName() + DELIMITER + nutrient.getValue() + nutrient.getUnit() + PER_GRAMS;
  }
}
