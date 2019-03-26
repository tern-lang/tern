package org.ternlang.compile;

public class PriorityQueueTest extends ScriptTestCase {
   
   private static final String SOURCE = 
   "let comparator = (a, b) -> Integer.compare(b.prefix.length, a.prefix.length);\n"+
   "let queue = PriorityQueue<Entry>(comparator);\n"+
   "class Entry {\n"+
   "   let prefix;\n"+
   "   new(prefix){\n"+
   "      this.prefix = prefix;\n"+
   "   }\n"+
   "}\n"+
   "queue.offer(Entry(\"a\"));\n"+
   "queue.offer(Entry(\"b\"));\n"+
   "queue.offer(Entry(\"ccc\"));\n"+
   "queue.offer(Entry(\"dd\"));\n"+
   "\n"+
   "assert queue.poll().prefix == \"ccc\";\n";         
   
   public void testQueue() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
