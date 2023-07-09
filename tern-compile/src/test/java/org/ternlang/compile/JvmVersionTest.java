package org.ternlang.compile;

import junit.framework.TestCase;

public class JvmVersionTest extends TestCase {

   public void testVersion() {
      int version = JvmVersion.getVersion();
      String spec =  JvmVersion.getSpecVersion();

      assertTrue("Version should be positive " + spec,version > 0);
   }
}
