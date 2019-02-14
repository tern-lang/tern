package org.ternlang.parse;

public interface RuleIterator {
   boolean hasNext();
   Rule peek();
   Rule next();
}