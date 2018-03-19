package com.nmj.systemproperties;

import java.util.Properties;

/**
 * Hello world!
 *
 */
public class MainClass {
    public static void main( String[] args ){
      Properties  properties = System.getProperties();
      properties.entrySet().forEach(entry -> {
        Object key = entry.getKey();
        Object value = entry.getValue();
        System.out.println("(" + key + ", " + value + ")");
      });
    }
}
