package tern.tree.condition;

import static java.lang.Boolean.FALSE;

public class BooleanChecker {

   public static boolean isTrue(Object value) {
      return value != null && !FALSE.equals(value);
   }
}
