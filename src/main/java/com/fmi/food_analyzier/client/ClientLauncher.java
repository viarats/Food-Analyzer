package com.fmi.food_analyzier.client;

import com.google.inject.Guice;

public class ClientLauncher {

  public static void main(final String[] args) {
    final var injector = Guice.createInjector(new ClientModule());
    final var client = injector.getInstance(Client.class);

    client.start();
  }
}
