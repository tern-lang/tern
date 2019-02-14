package org.ternlang.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class CompoundIteratorTest extends TestCase {
   
   public void testIterator() {
      Set<String> one = new LinkedHashSet<String>();
      List<String> two = new ArrayList<String>();
      List<String> three = new ArrayList<String>();
      
      one.add("a");
      one.add("b");
      one.add("c");
      
      two.add("c");
      two.add("d");
      two.add("e");
      
      three.add("a");
      
      Iterator<String> iterator = new CompoundIterator<String>(
            one.iterator(),
            two.iterator(),
            three.iterator());
      
      List<String> capture = new ArrayList<String>();
      
      while(iterator.hasNext()) {
         String value = iterator.next();
         System.err.println(value);
         capture.add(value);
      }
      assertEquals(capture.size(), 5);
      assertEquals(capture.get(0), "a");
      assertEquals(capture.get(1), "b");
      assertEquals(capture.get(2), "c");
      assertEquals(capture.get(3), "d");
      assertEquals(capture.get(4), "e");
   }

}
