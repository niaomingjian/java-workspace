package com.nmj.path;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MainClass {

  public static void main(String[] args) {
    Path path = Paths.get("/tmp20180312", "dir1", "subdir");
    path.toFile().mkdirs();
    System.out.print(path.toString());
  }

}
