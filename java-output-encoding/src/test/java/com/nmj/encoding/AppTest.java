package com.nmj.encoding;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AppTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  /**
   * Rigourous Test :-)
   * @throws UnsupportedEncodingException 
   */
  public void testApp() throws UnsupportedEncodingException {
    assertTrue(true);

    LOGGER.warn("LOGGER.warn TestAppTest:モック機能が動作しています。");
    System.out.print("System.out.print TestAppTest:モック機能が動作しています。");
    PrintStream out = new PrintStream(System.out, true, "UTF-8");
    out.print("System.out.print2 TestAppTest:モック機能が動作しています。");
    String str = "str";
    Assert.assertNull("nullではありません。モック機能が動作しています。", str);
  }
}
