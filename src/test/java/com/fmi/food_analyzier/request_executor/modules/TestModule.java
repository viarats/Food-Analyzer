package com.fmi.food_analyzier.request_executor.modules;

import static org.mockito.Mockito.mock;

import com.fmi.food_analyzier.formatter.FormatterModule;
import com.fmi.food_analyzier.httpclient.HttpClient;
import com.fmi.food_analyzier.request_executor.RequestExecutorModule;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.util.UUID;

public class TestModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new RequestExecutorModule());
    install(new FormatterModule());

    bind(String.class)
        .annotatedWith(Names.named("api.key"))
        .toInstance(UUID.randomUUID().toString());
    bind(String.class)
        .annotatedWith(Names.named("get.food.endpoint"))
        .toInstance(UUID.randomUUID().toString());
    bind(String.class)
        .annotatedWith(Names.named("get.food.report.endpoint"))
        .toInstance(UUID.randomUUID().toString());
    bind(HttpClient.class).toInstance(mock(HttpClient.class));
  }
}
