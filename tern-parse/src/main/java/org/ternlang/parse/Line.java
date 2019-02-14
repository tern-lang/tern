package org.ternlang.parse;

public interface Line {
   String getResource();
   String getSource();
   int getNumber();
}