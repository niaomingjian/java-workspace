package com.nmj.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class JarClassLoader {

  public Class<?> loadClass(String jarname, String className) {
    try {
      URL[] classLoaderUrls = new URL[] { new File("D:\\lib\\" + jarname).toURI().toURL() };
      try (URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);) {
        Class<?> beanClass = (Class<?>) urlClassLoader.loadClass(className);
        
        //Class.forName(beanClass.getName(), true, urlClassLoader);
        
        return beanClass;
      }
    } catch (Exception ex) {
      System.out.println(ex);
      return null;
    }
  }

  public Object loadObject(String jarname, String className) {
    Class<?> clz = this.loadClass(jarname, className);
    Object o = null;
    try {
      o = clz.newInstance();
    } catch (Exception ex) {
      System.out.println(ex);
      return null;
    }
    return o;
  }
  
  public Object loadObject2(String jarname, String className) {
    try {
      URL[] classLoaderUrls = new URL[] { new File("D:\\lib\\" + jarname).toURI().toURL() };
      try (URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);) {
        //Class<?> beanClass = (Class<?>) urlClassLoader.loadClass(className);
        // classloader加载一个jar中的类，这个类会引用jar中另一个类的静态成员。需要用Class.forName(className, true, urlClassLoader)来初始化
        return Class.forName(className, true, urlClassLoader);
      }
    } catch (Exception ex) {
      System.out.println(ex);
      return null;
    }
  }

}
