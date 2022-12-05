package org.ternlang.parse.insertion;

public interface ScopeNode {
   void update(Segment segment);
   default void close() {}
}
