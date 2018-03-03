package com.nmj.console;

public class ReadConsole3 {
  public static void main(String[] args) {
    System.out.println("ReadConsole3");
    while (true) {

      System.out.print("Enter something : ");
      String input = System.console().readLine();

      if ("q".equals(input)) {
        System.out.println("Exit!");
        System.exit(0);
      }

      System.out.println("input : " + input);
      System.out.println("-----------\n");
    }

  }
}
