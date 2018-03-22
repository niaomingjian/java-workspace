package com.nmj.javagenericunboundedwildcards;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainClass {

  public static void main(String[] args) {

    List<Integer> li = Stream.of(1, 2, 3).collect(Collectors.toList());
    List<String> ls = Stream.of("one", "two", "three").collect(Collectors.toList());
    printList(li);
    printList(ls);

    // @formatter:off
    // printListObj(li); //The method printListObj(List<Object>) in the type MainClass is not applicable for the arguments (List<Integer>
    // @formatter:on    

    printSize(li);
    printSize(ls);
  }

  public static void printListObj(List<Object> list) {
    for (Object elem : list)
      System.out.println(elem + " ");
    System.out.println();
    list.add("123");
  }

  // 1. using functionality provided in the Object class
  public static void printList(List<?> list) {
    for (Object elem : list)
      System.out.print(elem + " ");
    System.out.println();
    list.add(null);
  }

  // 2. using methods in the generic class that don't depend on the type
  // parameter
  public static void printSize(List<?> list) {
    System.out.println("-----code before");
    System.out.println(list.size());
    System.out.println("code after------");
  }

}
