package com.fmi.food_analyzier.client.handlers.modules;

import static org.mockito.Mockito.mock;

import com.fmi.food_analyzier.client.handlers.ResponseHandler;
import com.fmi.food_analyzier.communicator.Communicator;
import com.google.inject.AbstractModule;

public class ResponseHandlerTestModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ResponseHandler.class).asEagerSingleton();
    bind(Communicator.class).toInstance(mock(Communicator.class));
  }
}
