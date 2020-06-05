package com.fmi.food_analyzier.reader;

import java.util.Scanner;

class ConsoleReader implements Reader {
  private static final Scanner SCANNER = new Scanner(System.in);

  @Override
  public String read() {
    return SCANNER.nextLine();
  }
}
