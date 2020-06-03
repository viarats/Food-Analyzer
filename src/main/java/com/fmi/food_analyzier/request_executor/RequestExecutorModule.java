package com.fmi.food_analyzier.request_executor;

import com.google.gson.Gson;
import com.google.inject.PrivateModule;

public class RequestExecutorModule extends PrivateModule {

  @Override
  protected void configure() {
    bind(RequestExecutor.class).to(RequestExecutorImpl.class).asEagerSingleton();
    expose(RequestExecutor.class);

    bind(Gson.class).asEagerSingleton();
    expose(Gson.class);
  }
}
