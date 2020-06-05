package com.fmi.food_analyzier.writer;

class ConsoleWriter implements Writer {

  @Override
  public void write(final String message) {
    System.out.print(message);
  }

  @Override
  public void writeLine(final String message) {
    System.out.println(message);
  }
}
