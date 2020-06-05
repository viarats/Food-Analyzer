package com.fmi.food_analyzier.server;

import com.fmi.food_analyzier.server.handlers.RequestHandler;
import com.google.inject.PrivateModule;
import io.netty.channel.ChannelHandler;

public class ServerModule extends PrivateModule {

  @Override
  protected void configure() {
    bind(Server.class).to(NettyServer.class).asEagerSingleton();
    expose(Server.class);

    bind(ChannelHandler.class)
        .annotatedWith(ServerHandler.class)
        .to(RequestHandler.class)
        .asEagerSingleton();
  }
}
