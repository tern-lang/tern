package org.ternlang.parse;

public class SpaceGrammar implements Grammar {

   private final GrammarMatcher matcher;
   
   public SpaceGrammar() {
      this.matcher = new SpaceMatcher();
   }
   
   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      return matcher;
   } 
   
   private static class SpaceMatcher implements GrammarMatcher {
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {
         return checker.space();
      }
      
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {
         return builder.space();
      }
      
      @Override
      public String toString() {
         return "_";
      }
   }
}