package com.nmj.classextend;

public class MainClass {

  public static void main(String[] args) {
    // 1.使用父类引用来调用方法print
    Employee e = new SalesEmployee();
    e.print();
    
    // 2.使用类自身引用来调用方法print    
    SalesEmployee se = (SalesEmployee)e;
    se.print();
    
    /**output:
     * I'm Sales Employee
     * I'm Sales Employee
     * 
     * 結果,调用的都是类自身的print
     */
  }

}
