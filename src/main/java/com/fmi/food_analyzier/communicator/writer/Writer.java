package com.fmi.food_analyzier.communicator.writer;

public interface Writer {
  void write(String message);

  void writeLine(String message);
}
