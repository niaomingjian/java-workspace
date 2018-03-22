package com.nmj.javagenericupperboundedwildcards;

import java.util.Arrays;
import java.util.List;

public class MainClass {

  public static void main(String[] args) {

    List<Integer> li = Arrays.asList(1, 2, 3);
    System.out.println("sum = " + sumOfList(li));
    System.out.println("sum = " + sumOfListWildcards(li));
    System.out.println("sum = " + sumOfListInteger(li));
    
    List<Double> ld = Arrays.asList(1.2, 2.3, 3.5);
    System.out.println("sum = " + sumOfList(ld));
    System.out.println("sum = " + sumOfListWildcards(ld));
    
    // @formatter:off
    // System.out.println("sum = " + sumOfListInteger(ld)); // The method sumOfListInteger(List<Integer>) in the type MainClass is not applicable for the arguments (List<Double>
    // @formatter:on

    
  }

  // 1.
  public static double sumOfListInteger(List<Integer> list) {
    double s = 0.0;
    for (Integer n : list)
      s += n.doubleValue();
    return s;
  }
  
  // 2.
  // public static double sumOfListWildcards(List<T extends Number> list) ×
  public static double sumOfListWildcards(List<? extends Number> list) {
    double s = 0.0;
    for (Number n : list)
      s += n.doubleValue();
    return s;
  }
  
  // 3.
  public static <T extends Number> double sumOfList(List<T> list) {
    double s = 0.0;
    for (T n : list)
      s += n.doubleValue();
    return s;
  }
}
