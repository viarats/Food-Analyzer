package com.fmi.food_analyzier.server.handlers.modules;

import static org.mockito.Mockito.mock;

import com.fmi.food_analyzier.request_executor.RequestExecutor;
import com.fmi.food_analyzier.server.handlers.RequestHandler;
import com.google.inject.AbstractModule;

public class RequestHandlerTestModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(RequestHandler.class).asEagerSingleton();
    bind(RequestExecutor.class).toInstance(mock(RequestExecutor.class));
  }
}
