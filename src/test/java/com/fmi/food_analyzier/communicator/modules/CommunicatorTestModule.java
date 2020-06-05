package com.fmi.food_analyzier.communicator.modules;

import static org.mockito.Mockito.mock;

import com.fmi.food_analyzier.communicator.CommunicatorModule;
import com.fmi.food_analyzier.reader.Reader;
import com.fmi.food_analyzier.writer.Writer;
import com.google.inject.AbstractModule;

public class CommunicatorTestModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new CommunicatorModule());

    bind(Reader.class).toInstance(mock(Reader.class));
    bind(Writer.class).toInstance(mock(Writer.class));
  }
}
