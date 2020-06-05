package com.fmi.food_analyzier.communicator;

import com.google.inject.PrivateModule;

public class CommunicatorModule extends PrivateModule {

  @Override
  protected void configure() {
    bind(Communicator.class).to(UserCommunicator.class).asEagerSingleton();
    expose(Communicator.class);
  }
}
