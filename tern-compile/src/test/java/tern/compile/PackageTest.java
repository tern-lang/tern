package tern.compile;

import junit.framework.TestCase;

public class PackageTest extends TestCase {
   
   public void testPackage() throws Exception {

      // create a package object for java.lang package
      Package pack1 = Package.getPackage("java.lang");
      Package pack2 = Package.getPackage("java.lang.reflect");
      Package pack3 = Package.getPackage("java.util.regex");

      // get the fully qualified name for this package
      System.out.println(pack1);
      System.out.println(pack2);
      System.out.println(pack3);
   }

}
