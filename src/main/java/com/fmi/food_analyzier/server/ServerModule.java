package com.fmi.food_analyzier.server;

import com.fmi.food_analyzier.configuration.SystemConfigurationModule;
import com.fmi.food_analyzier.httpclient.HttpClientModule;
import com.fmi.food_analyzier.request_executor.RequestExecutorModule;
import com.fmi.food_analyzier.server.handlers.RequestHandler;
import com.google.inject.PrivateModule;
import io.netty.channel.ChannelHandler;

class ServerModule extends PrivateModule {

  @Override
  protected void configure() {
    install(new SystemConfigurationModule());
    install(new RequestExecutorModule());
    install(new HttpClientModule());

    bind(Server.class).to(NettyServer.class).asEagerSingleton();
    expose(Server.class);

    bind(ChannelHandler.class)
        .annotatedWith(ServerHandler.class)
        .to(RequestHandler.class)
        .asEagerSingleton();
  }
}
