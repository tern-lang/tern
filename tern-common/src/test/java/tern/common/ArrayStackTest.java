package tern.common;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ArrayStackTest extends TestCase {

   public void testStack() throws Exception {
      Stack<String> stack = new ArrayStack<String>();
      
      stack.push("a");
      stack.push("b");
      stack.push("c");
      
      assertEquals(stack.peek(), "c");
      assertEquals(stack.size(), 3);
      assertFalse(stack.isEmpty());
      
      List<String> list = new ArrayList<String>();
      
      for(String entry : stack){
         System.err.println(entry);
         list.add(entry);
      }
      assertEquals(list.get(0), "c");
      assertEquals(list.get(1), "b");
      assertEquals(list.get(2), "a");
   }
}
