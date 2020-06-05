package com.fmi.food_analyzier.client;

import com.fmi.food_analyzier.client.handlers.ResponseHandler;
import com.fmi.food_analyzier.communicator.CommunicatorModule;
import com.fmi.food_analyzier.configuration.SystemConfigurationModule;
import com.fmi.food_analyzier.reader.ReaderModule;
import com.fmi.food_analyzier.writer.WriterModule;
import com.google.inject.PrivateModule;
import io.netty.channel.ChannelHandler;

class ClientModule extends PrivateModule {

  @Override
  protected void configure() {
    install(new SystemConfigurationModule());
    install(new ReaderModule());
    install(new WriterModule());

    install(new CommunicatorModule());

    bind(ChannelHandler.class)
        .annotatedWith(ClientHandler.class)
        .to(ResponseHandler.class)
        .asEagerSingleton();

    bind(Client.class).to(NettyClient.class).asEagerSingleton();
    expose(Client.class);
  }
}
