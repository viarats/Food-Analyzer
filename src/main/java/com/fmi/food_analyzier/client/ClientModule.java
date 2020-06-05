package com.fmi.food_analyzier.client;

import com.fmi.food_analyzier.client.handlers.ResponseHandler;
import com.google.inject.PrivateModule;
import io.netty.channel.ChannelHandler;

public class ClientModule extends PrivateModule {

  @Override
  protected void configure() {
    bind(Client.class).to(NettyClient.class).asEagerSingleton();
    expose(Client.class);

    bind(ChannelHandler.class)
        .annotatedWith(ClientHandler.class)
        .to(ResponseHandler.class)
        .asEagerSingleton();
  }
}
