package org.ternlang.parse;

import junit.framework.TestCase;

public class ArrayTableTest extends TestCase {
   
   public void testTable() throws Exception {
      Object[] array = new Object[200];
      ArrayTable<String> table = new ArrayTable<String>(array, 50, 100);
      
      table.put(1, "ONE");
      table.put(2, "TWO");
      table.put(3, "THREE");
      
      assertEquals(table.get(1), "ONE");
      assertEquals(table.get(2), "TWO");
      assertEquals(table.get(3), "THREE");
   }

}
