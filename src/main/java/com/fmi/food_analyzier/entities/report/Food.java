package com.fmi.food_analyzier.entities.report;

import com.fmi.food_analyzier.entities.enums.NutrientType;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Food {
  private final String name;

  @SerializedName("ing")
  private final Ingredient ingredients;

  private final Set<Nutrient> nutrients;

  public Food(final String name, final Ingredient ingredients, final Set<Nutrient> nutrients) {
    this.name = name;
    this.ingredients = ingredients;
    this.nutrients = nutrients;
  }

  public String getName() {
    return name;
  }

  public Ingredient getIngredients() {
    return ingredients;
  }

  public Set<Nutrient> getNutrients() {
    return nutrients;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("Name: ");
    builder.append(name);

    Optional.ofNullable(ingredients)
        .ifPresent(
            ingredients -> builder.append("\nIngredients: ").append(ingredients.getDescription()));

    builder.append("\n");
    Arrays.stream(NutrientType.values()).forEach(type -> appendNutrient(builder, type));

    return builder.toString();
  }

  private void appendNutrient(final StringBuilder stringBuilder, final NutrientType type) {
    stringBuilder
        .append(
            nutrients.stream()
                .filter(nutrient -> nutrient.getName().endsWith(type.getName()))
                .map(Nutrient::toString)
                .collect(Collectors.joining()))
        .append("\n");
  }
}
