package com.fmi.food_analyzier.launchers.client;

import com.fmi.food_analyzier.client.ClientModule;
import com.fmi.food_analyzier.communicator.CommunicatorModule;
import com.fmi.food_analyzier.configuration.SystemConfigurationModule;
import com.fmi.food_analyzier.reader.ReaderModule;
import com.fmi.food_analyzier.writer.WriterModule;
import com.google.inject.AbstractModule;

class ClientLauncherModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new SystemConfigurationModule());
    install(new CommunicatorModule());
    install(new ReaderModule());
    install(new WriterModule());
    install(new ClientModule());
  }
}
