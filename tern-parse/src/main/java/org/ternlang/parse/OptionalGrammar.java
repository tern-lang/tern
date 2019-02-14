package org.ternlang.parse;

public class OptionalGrammar implements Grammar {

   private final Grammar grammar; 
   
   public OptionalGrammar(Grammar grammar) {
      this.grammar = grammar;
   }
   
   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      GrammarMatcher matcher = grammar.create(cache, length);
      return new OptionalMatcher(matcher);
   } 
   
   private static class OptionalMatcher implements GrammarMatcher {
      
      private final GrammarMatcher matcher;
      
      public OptionalMatcher(GrammarMatcher matcher) {
         this.matcher = matcher; 
      }
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {      
         matcher.check(checker, depth);
         return true;
      }
   
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {      
         matcher.build(builder, depth);
         return true;
      }
      
      @Override
      public String toString() {
         return String.format("?%s", matcher);
      }    
   }
}