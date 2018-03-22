package com.nmj.forloop;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainClass {

  public static void main(String[] args) {
    String[] arr = new String[] { "str1", "str2", "stop", "str4" };

    System.out.println("----------------test1-------------");
    // array - for - iterate over array
    for (int i = 0; i < arr.length; i++) {

    }

    System.out.println("----------------test2-------------");
    // array - foreach - iterate over array
    for (String str : arr) {
      System.out.println(str);
      if ("stop".equals(str)) {
        break;
      }
    }

    List<String> list = Stream.of("str1", "str2", "stop", "str4").collect(Collectors.toList());

    System.out.println("----------------test3-------------");
    // list - for - iterate over collection
    for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
      String str = (String) iterator.next();
      System.out.println(str);
      if ("stop".equals(str)) {
        break;
      }
    }

    System.out.println("----------------test4-------------");
    // list - foreach - iterate over Iterable
    for (String str : list) {
      System.out.println(str);
      if ("stop".equals(str)) {
        break;
      }
    }
    // @formatter:off    
//    System.out.println("----------------test5-------------");
//    list.forEach(str ->{
//      System.out.println(str);
//      if ("stop".equals(str)) {
//        break;  // compile error: break cannot be used outside of a loop or a switch
//      }
//    });
    // @formatter:on

    // @formatter:off
    // output:
    // ----------------test1-------------
    // ----------------test2-------------
    // str1
    // str2
    // stop
    // ----------------test3-------------
    // str1
    // str2
    // stop
    // ----------------test4-------------
    // str1
    // str2
    // stop
    // @formatter:on

  }

}
