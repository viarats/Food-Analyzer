package com.fmi.food_analyzier.entities;

import java.io.Serializable;

public class Product implements Serializable {
  private final ProductList list;

  public Product(final ProductList list) {
    this.list = list;
  }

  public ProductList getList() {
    return list;
  }
}
