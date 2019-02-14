package org.ternlang.parse;

import java.util.Arrays;
import java.util.List;

import org.ternlang.common.BitSet;

public class MatchAllGrammar implements Grammar {

   private final List<Grammar> grammars;
   private final String name;
   private final int count;
   private final int index;
   
   public MatchAllGrammar(List<Grammar> grammars, String name, int count, int index) {
      this.grammars = grammars;
      this.index = index;
      this.count = count;
      this.name = name;
   }  

   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      GrammarMatcher[] matchers = new GrammarMatcher[count];
      
      for(int i = 0; i < count; i++) {
         Grammar grammar = grammars.get(i);
         matchers[i] = grammar.create(cache, length);
      }
      return new MatchAllMatcher(matchers, name, index, length);
   } 
   
   public static class MatchAllMatcher implements GrammarMatcher {
      
      private final GrammarMatcher[] matchers;
      private final BitSet success;
      private final BitSet failure;
      private final String name;
      private final int index;

      public MatchAllMatcher(GrammarMatcher[] matchers, String name, int index, int length) {
         this.success = new BitSet(length);
         this.failure = new BitSet(length);
         this.matchers = matchers;
         this.index = index;
         this.name = name;
      }
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {
         int position = checker.position();
         
         if(depth == 0) {
            for(GrammarMatcher matcher : matchers) {               
               if(!matcher.check(checker, depth + 1)) {
                  return false; 
               }
            }
            return true;
         }
         if(!failure.get(position)) {
            if(!success.get(position)) {
               int mark = checker.mark(index);   
               int count = 0;
               
               if(mark != -1) {            
                  for(GrammarMatcher grammar : matchers) {               
                     if(!grammar.check(checker, 0)) {
                        checker.reset(mark, index);
                        failure.set(position);
                        return false;
                     }
                     count++;                     
                  }
                  checker.reset(mark, index);
               }           
               if(count == matchers.length) {
                  success.set(position);
               }
            }
            if(success.get(position)) {
               for(GrammarMatcher grammar : matchers) {               
                  if(!grammar.check(checker, 0)) {
                     throw new ParseException("Could not read node in " + name);  
                  }
               }
               return true;
            } 
         }
         return false;
      }
   
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {
         int position = builder.position();
         
         if(depth == 0) {
            for(GrammarMatcher matcher : matchers) {               
               if(!matcher.build(builder, depth + 1)) {
                  return false; 
               }
            }
            return true;
         }
         if(!failure.get(position)) {
            if(!success.get(position)) {
               SyntaxBuilder child = builder.mark(index);   
               int count = 0;
               
               if(child != null) {            
                  for(GrammarMatcher grammar : matchers) {               
                     if(!grammar.build(child, 0)) {
                        failure.set(position);
                        break;
                     }
                     count++;
                  }
                  child.reset();
               }           
               if(count == matchers.length) {
                  success.set(position);
               }
            }
            if(success.get(position)) {
               for(GrammarMatcher grammar : matchers) {               
                  if(!grammar.build(builder, 0)) {
                     throw new ParseException("Could not read node in " + name);  
                  }
               }
               return true;
            } 
         }
         return false;
      }
      
      @Override
      public String toString() {
         return Arrays.toString(matchers);
      }
   }
}