package com.fmi.food_analyzier.request_executor.utils;

import com.fmi.food_analyzier.entities.Item;
import com.fmi.food_analyzier.entities.ProductList;
import com.fmi.food_analyzier.entities.enums.NutrientType;
import com.fmi.food_analyzier.entities.report.Food;
import com.fmi.food_analyzier.entities.report.Nutrient;
import com.fmi.food_analyzier.entities.report.NutrientData;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestExecutorUtils {
  private static final int LIMIT = 15;

  public static ProductList generateRandomProductList() {
    return new ProductList(generateRandomItems());
  }

  private static Set<Item> generateRandomItems() {
    final int size = ThreadLocalRandom.current().nextInt(LIMIT);
    return Stream.generate(RequestExecutorUtils::generateRandomItem)
        .limit(size)
        .collect(Collectors.toUnmodifiableSet());
  }

  private static Item generateRandomItem() {
    return new Item(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString());
  }

  public static Food generateRandomReport() {
    return new Food(
        UUID.randomUUID().toString(), UUID.randomUUID().toString(), generateRandomNutrients());
  }

  private static Set<Nutrient> generateRandomNutrients() {
    final int size = ThreadLocalRandom.current().nextInt(LIMIT);
    return Stream.generate(RequestExecutorUtils::generateRandomNutrient)
        .limit(size)
        .collect(Collectors.toUnmodifiableSet());
  }

  private static Nutrient generateRandomNutrient() {
    return new Nutrient(
        new NutrientData(
            UUID.randomUUID().toString() + getRandomNutrientName(), UUID.randomUUID().toString()),
        ThreadLocalRandom.current().nextFloat());
  }

  private static String getRandomNutrientName() {
    final var index = ThreadLocalRandom.current().nextInt(NutrientType.values().length);
    return Arrays.stream(NutrientType.values())
        .map(NutrientType::getValue)
        .skip(index)
        .findFirst()
        .orElse(null);
  }
}
