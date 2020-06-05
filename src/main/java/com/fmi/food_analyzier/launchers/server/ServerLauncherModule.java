package com.fmi.food_analyzier.launchers.server;

import com.fmi.food_analyzier.configuration.SystemConfigurationModule;
import com.fmi.food_analyzier.httpclient.HttpClientModule;
import com.fmi.food_analyzier.request_executor.RequestExecutorModule;
import com.fmi.food_analyzier.server.ServerModule;
import com.google.inject.AbstractModule;

class ServerLauncherModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new SystemConfigurationModule());
    install(new RequestExecutorModule());
    install(new HttpClientModule());
    install(new ServerModule());
  }
}
