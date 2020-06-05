package com.fmi.food_analyzier.launchers.client;

import com.fmi.food_analyzier.client.Client;
import com.google.inject.Guice;

class ClientLauncher {

  public static void main(final String[] args) {
    final var injector = Guice.createInjector(new ClientLauncherModule());
    final var client = injector.getInstance(Client.class);

    client.start();
  }
}
