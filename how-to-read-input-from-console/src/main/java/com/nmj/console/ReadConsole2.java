package com.nmj.console;

import java.util.Scanner;

public class ReadConsole2 {

  public static void main(String[] args) {
    System.out.println("ReadConsole2");
    Scanner scanner = new Scanner(System.in);

    while (true) {

      System.out.print("Enter something : ");
      String input = scanner.nextLine();

      if ("q".equals(input)) {
        System.out.println("Exit!");
        break;
      }

      System.out.println("input : " + input);
      System.out.println("-----------\n");
    }

    scanner.close();

  }

}
