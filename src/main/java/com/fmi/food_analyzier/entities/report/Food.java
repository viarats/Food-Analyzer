package com.fmi.food_analyzier.entities.report;

import com.google.gson.annotations.SerializedName;
import java.util.Set;

public class Food {

  @SerializedName("description")
  private final String name;

  private final String ingredients;

  @SerializedName("foodNutrients")
  private final Set<Nutrient> nutrients;

  public Food(final String name, final String ingredients, final Set<Nutrient> nutrients) {
    this.name = name;
    this.ingredients = ingredients;
    this.nutrients = nutrients;
  }

  public String getName() {
    return name;
  }

  public String getIngredients() {
    return ingredients;
  }

  public Set<Nutrient> getNutrients() {
    return nutrients;
  }
}
