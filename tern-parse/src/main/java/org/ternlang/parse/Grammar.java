package org.ternlang.parse;

public interface Grammar {   
   GrammarMatcher create(GrammarCache cache, int length);
}