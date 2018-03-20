package com.nmj.classloader.addon;

import java.util.ArrayList;
import java.util.List;

public class Model {

  public static List<Field> fieldList;

  static {
    fieldList = new ArrayList<>();
    fieldList.add(Property.P_FIELD1);
    fieldList.add(Property.P_FIELD2);
  }
}
