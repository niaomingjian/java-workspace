package com.nmj.classloader;

public class MainClass {

  public static void main(String[] args) {
    JarClassLoader jcl = new JarClassLoader();
    String jarname = "classloader-two-related-class-addon-0.0.1-SNAPSHOT.jar";
    String className = "com.nmj.classloader.addon.Model";
//    Class<?> clazz = jcl.loadClass(jarname, className);
//    System.out.println(clazz.getClassLoader());
    
    
    // NoClassDefFoundError
//    Object o = jcl.loadObject(jarname, className);
//    System.out.println(o);
    
    Object o = jcl.loadObject2(jarname, className);
    System.out.println(o);
  }

}
