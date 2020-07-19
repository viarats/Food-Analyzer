package com.fmi.food_analyzier.formatter;

import static com.fmi.food_analyzier.formatter.Formatter.NO_AVAILABLE_INFORMATION_MESSAGE;
import static com.fmi.food_analyzier.formatter.utils.FormatterUtils.formatBrandedProduct;
import static com.fmi.food_analyzier.formatter.utils.FormatterUtils.formatProduct;
import static com.fmi.food_analyzier.formatter.utils.FormatterUtils.formatReport;
import static com.fmi.food_analyzier.formatter.utils.FormatterUtils.generateItems;
import static com.fmi.food_analyzier.request_executor.utils.RequestExecutorUtils.generateRandomReport;
import static org.testng.Assert.assertEquals;

import com.fmi.food_analyzier.entities.ProductList;
import com.google.inject.Inject;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = FormatterModule.class)
public class FormatterTest {
  private static final String UPC = UUID.randomUUID().toString();
  private static final String BRANDED_FOODS_GROUP = "Branded Food Products Database";
  private static final String BRANDED_FOOD_NAME =
      UUID.randomUUID().toString() + " " + UUID.randomUUID().toString();
  private static final String BRANDED_FOOD = BRANDED_FOOD_NAME + " UPC: " + UPC;
  private static final String NEW_LINE = "\n";

  @Inject private Formatter formatter;

  @DataProvider(name = "productData")
  private Object[][] provideProductData() {
    final var items = generateItems(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    final var expectedProduct =
        items.stream()
                .map(item -> formatProduct(item.getName(), item.getNdbno()))
                .collect(Collectors.joining(NEW_LINE))
            + NEW_LINE;

    final var brandedItems = generateItems(BRANDED_FOODS_GROUP, BRANDED_FOOD);
    final var expectedBrandedProduct =
        brandedItems.stream()
                .map(item -> formatBrandedProduct(BRANDED_FOOD_NAME, item.getNdbno(), UPC))
                .collect(Collectors.joining(NEW_LINE))
            + NEW_LINE;

    return new Object[][] {
      {new ProductList(items), expectedProduct},
      {new ProductList(brandedItems), expectedBrandedProduct}
    };
  }

  @Test(dataProvider = "productData")
  void testFormatProduct(final ProductList productList, final String expected) {
    final var actual = formatter.formatProductList(productList);
    assertEquals(actual, expected);
  }

  @Test
  void testFormatProductWithEmptyProductListShouldReturnNoInformationMessage() {
    final var productList = new ProductList(Set.of());
    final var actual = formatter.formatProductList(productList);

    assertEquals(actual, NO_AVAILABLE_INFORMATION_MESSAGE);
  }

  @Test
  void testFormatFoodReport() {
    final var report = generateRandomReport();

    final var actual = formatter.formatFoodReport(report);
    final var expected = formatReport(report);

    assertEquals(actual, expected);
  }
}
