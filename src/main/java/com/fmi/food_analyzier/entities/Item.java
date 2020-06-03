package com.fmi.food_analyzier.entities;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Item {
  private static final String BRANDED_FOODS = "Branded Food Products Database";

  private final String name;
  private final String ndbno;
  private final String group;
  private final String upc;

  public Item(final String name, final String ndbno, final String group, final String upc) {
    this.name = name;
    this.ndbno = ndbno;
    this.group = group;
    this.upc = upc;
  }

  public String getName() {
    return name;
  }

  public String getNdbno() {
    return ndbno;
  }

  public String getGroup() {
    return group;
  }

  public String getUpc() {
    return upc;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Item item = (Item) o;
    return Objects.equals(name, item.name)
        && Objects.equals(ndbno, item.ndbno)
        && Objects.equals(group, item.group)
        && Objects.equals(upc, item.upc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, ndbno, group, upc);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("Name: ");

    if (group.equals(BRANDED_FOODS)) {
      final String[] tokens = name.split("\\s+");
      final var upc = tokens[tokens.length - 1];
      final var name = extractName(tokens);

      builder.append(name).append(", NDBNO: ").append(ndbno).append(", UPC: ").append(upc);
    } else {
      builder.append(name).append(", NDBNO: ").append(ndbno);
    }

    return builder.toString();
  }

  private String extractName(final String[] tokens) {
    return IntStream.range(0, tokens.length - 2)
        .boxed()
        .map(index -> tokens[index])
        .collect(Collectors.joining());
  }
}
