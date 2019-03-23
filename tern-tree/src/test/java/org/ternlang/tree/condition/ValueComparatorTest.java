package org.ternlang.tree.condition;

import junit.framework.TestCase;
import org.ternlang.core.variable.Value;

public class ValueComparatorTest extends TestCase {

   public void testValueComparator() throws Exception {
      assertComparator(1, 2, ValueComparator.INTEGER_INTEGER);
      assertComparator(1, 20000L, ValueComparator.INTEGER_INTEGER);
      assertComparator(1.0, 20000L, ValueComparator.DECIMAL_DECIMAL);
      assertComparator(1, 'c', ValueComparator.INTEGER_CHARACTER);
      assertComparator('c', 2, ValueComparator.CHARACTER_INTEGER);
      assertComparator('c', 'a', ValueComparator.COMPARABLE_COMPARABLE);
      assertComparator("one", "two", ValueComparator.COMPARABLE_COMPARABLE);
      assertComparator("c", 'c', ValueComparator.STRING_CHARACTER);
      assertComparator('c', "c", ValueComparator.CHARACTER_STRING);
      assertComparator(true, "c", ValueComparator.OBJECT_OBJECT);
      assertComparator(1.0, 'c', ValueComparator.INTEGER_CHARACTER);
      assertComparator('d', 22.0, ValueComparator.CHARACTER_INTEGER);
      assertComparator(22.0, 22.0, ValueComparator.DECIMAL_DECIMAL);
      assertComparator(22.0d, 22.0f, ValueComparator.DECIMAL_DECIMAL);
      assertComparator(null, 22.0f, ValueComparator.OBJECT_OBJECT);
      assertComparator(null, null, ValueComparator.OBJECT_OBJECT);
      assertComparator(22, null, ValueComparator.OBJECT_OBJECT);
      assertComparator("foo", null, ValueComparator.OBJECT_OBJECT);
   }

   private void assertComparator(Object left, Object right, ValueComparator comparator) throws Exception {
      assertEquals(ValueComparator.resolveComparator(Value.getTransient(left), Value.getTransient(right)), comparator);
   }
}
