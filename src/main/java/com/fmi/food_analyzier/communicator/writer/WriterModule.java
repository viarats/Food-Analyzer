package com.fmi.food_analyzier.communicator.writer;

import com.google.inject.PrivateModule;

public class WriterModule extends PrivateModule {

  @Override
  protected void configure() {
    bind(Writer.class).to(ConsoleWriter.class).asEagerSingleton();
    expose(Writer.class);
  }
}
