package com.fmi.food_analyzier.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertiesLoader {
  public Properties loadProperties(final String filename) {
    try (final var inputStream = getInputStream(filename)) {
      final var properties = new Properties();
      properties.load(inputStream);
      return properties;
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  private InputStream getInputStream(final String filename) {
    return Optional.ofNullable(getClass().getClassLoader().getResourceAsStream(filename))
        .orElseThrow(
            () ->
                new RuntimeException(
                    String.format("Could not load inputstream of file => %s", filename)));
  }
}
