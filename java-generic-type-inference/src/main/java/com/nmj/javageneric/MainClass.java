package com.nmj.javageneric;

import java.io.Serializable;
import java.util.ArrayList;

public class MainClass {

  public static void main(String[] args) {
    Serializable s = pick("d", new ArrayList<String>());
    System.out.println(s);
  }

  static <T> T pick(T a1, T a2) { return a2; }
  
}
