package com.fmi.food_analyzier.launchers.server;

import com.fmi.food_analyzier.server.Server;
import com.google.inject.Guice;

class ServerLauncher {

  public static void main(final String[] args) {
    final var injector = Guice.createInjector(new ServerLauncherModule());
    final var server = injector.getInstance(Server.class);

    server.start();
  }
}
