package com.fmi.food_analyzier.reader;

import com.google.inject.PrivateModule;

public class ReaderModule extends PrivateModule {

  @Override
  protected void configure() {
    bind(Reader.class).to(ConsoleReader.class).asEagerSingleton();
    expose(Reader.class);
  }
}
