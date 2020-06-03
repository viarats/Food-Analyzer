package com.fmi.food_analyzier.configuration;

import com.fmi.food_analyzier.utils.PropertiesLoader;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class SystemConfigurationModule extends AbstractModule {
  private static final String FILENAME = "system_configuration.properties";

  @Override
  protected void configure() {
    final var properties = new PropertiesLoader().loadProperties(FILENAME);
    Names.bindProperties(binder(), properties);
  }
}
