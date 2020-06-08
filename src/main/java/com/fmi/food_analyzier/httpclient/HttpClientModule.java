package com.fmi.food_analyzier.httpclient;

import com.google.inject.PrivateModule;

public class HttpClientModule extends PrivateModule {
  @Override
  protected void configure() {
    bind(HttpClient.class).to(HttpClientImpl.class).asEagerSingleton();
    expose(HttpClient.class);
  }
}
