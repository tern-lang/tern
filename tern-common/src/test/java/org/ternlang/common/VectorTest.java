package org.ternlang.common;

import java.util.Iterator;

import junit.framework.TestCase;

public class VectorTest extends TestCase {
   
   public void testVector() {
      Vector<String> vector = new Vector<String>();
      vector.add("a");
      vector.add("b");
      vector.add("c");
      
      Iterator<String> iterator = vector.iterator();
   
      assertTrue(iterator.hasNext());
      assertEquals(iterator.next(), "a");
      assertTrue(iterator.hasNext());
      assertEquals(iterator.next(), "b");
      assertTrue(iterator.hasNext());
      assertEquals(iterator.next(), "c");      
   }

}
