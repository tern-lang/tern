package org.ternlang.common;

import junit.framework.TestCase;

public class ArraySliceTest extends TestCase {

   public void testArraySlice() {
      Array<String> a = new ArraySlice(new String[]{"a", "b", "c"}, 0, 2);

      assertEquals(a.length(), 2);
      assertEquals(a.get(0), "a");
      assertEquals(a.get(1), "b");

      {
         boolean failure = false;

         try {
            a.get(2);
         } catch(Exception e) {
            e.printStackTrace();
            failure = true;
         }
         assertTrue( "Index should be out of bounds", failure);
      }

      {
         boolean failure = false;

         try {
            a.get(-1);
         } catch(Exception e) {
            e.printStackTrace();
            failure = true;
         }
         assertTrue( "Index should be out of bounds", failure);
      }
   }
}
