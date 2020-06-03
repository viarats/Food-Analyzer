package com.fmi.food_analyzier.communicator;

import com.fmi.food_analyzier.communicator.reader.ReaderModule;
import com.fmi.food_analyzier.communicator.writer.WriterModule;
import com.google.inject.PrivateModule;

public class CommunicatorModule extends PrivateModule {

  @Override
  protected void configure() {
    install(new ReaderModule());
    install(new WriterModule());

    bind(Communicator.class).to(UserCommunicator.class).asEagerSingleton();
    expose(Communicator.class);
  }
}
