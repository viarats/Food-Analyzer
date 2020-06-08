package com.fmi.food_analyzier.formatter;

import com.google.inject.PrivateModule;

public class FormatterModule extends PrivateModule {

  @Override
  protected void configure() {
    bind(Formatter.class).to(EntityFormatter.class).asEagerSingleton();
    expose(Formatter.class);
  }
}
