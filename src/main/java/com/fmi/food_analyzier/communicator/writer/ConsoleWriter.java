package com.fmi.food_analyzier.communicator.writer;

class ConsoleWriter implements Writer {

  @Override
  public void write(final String message) {
      System.out.println(message);
  }
}
