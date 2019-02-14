package org.ternlang.parse;

public interface GrammarMatcher {
   boolean check(SyntaxChecker checker, int depth);
   boolean build(SyntaxBuilder builder, int depth);
}