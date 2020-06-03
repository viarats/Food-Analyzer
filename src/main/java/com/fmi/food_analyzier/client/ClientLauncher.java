package com.fmi.food_analyzier.client;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ClientLauncher {
  private static final Injector INJECTOR = Guice.createInjector(new ClientModule());

  public static void main(final String[] args) {
    //    final var injector = Guice.createInjector(new ClientModule());
    final var client = INJECTOR.getInstance(Client.class);

    client.start();
  }
}
