package com.nmj.javaassertbefore;

public class App {
  public static void main(String[] args) {
    Baz.testAsserts();
    // Will execute after Baz is initialized.
  }
}

class Bar {
  static {
    Baz.testAsserts();
    // Will execute before Baz is initialized!
  }
}

class Baz extends Bar {
  static void testAsserts() {
    boolean enabled = false;
    assert enabled = true;
    System.out.println("Asserts " + (enabled ? "enabled" : "disabled"));
  }
}

// @formatter:off
//
// output:
// Asserts enabled
// Asserts disabled
// 
// @formatter:on
