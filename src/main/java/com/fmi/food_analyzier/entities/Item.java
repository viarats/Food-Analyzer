package com.fmi.food_analyzier.entities;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class Item {
  @SerializedName("description")
  private final String name;

  @SerializedName("dataType")
  private final String group;

  @SerializedName("fdcId")
  private final String ndbno;

  @SerializedName("gtinUpc")
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

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final var item = (Item) o;
    return Objects.equals(name, item.name)
        && Objects.equals(ndbno, item.ndbno)
        && Objects.equals(group, item.group)
        && Objects.equals(upc, item.upc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, ndbno, group, upc);
  }
}
