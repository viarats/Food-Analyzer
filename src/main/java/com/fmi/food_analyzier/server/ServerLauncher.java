package com.fmi.food_analyzier.server;

import com.google.inject.Guice;

public class ServerLauncher {
  public static void main(final String[] args) {
    final var injector = Guice.createInjector(new ServerModule());
    final var server = injector.getInstance(Server.class);

    server.start();
  }
}
