package org.ternlang.parse;

public interface Token<T> {
   T getValue();
   Line getLine();
   short getType();
}