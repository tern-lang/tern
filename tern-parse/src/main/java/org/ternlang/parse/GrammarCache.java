package org.ternlang.parse;

public class GrammarCache {

   private GrammarMatcher[] matchers;

   public GrammarCache() {
      this(1024);
   }
   
   public GrammarCache(int size) {
      this.matchers = new GrammarMatcher[size]; 
   }  
   
   public GrammarMatcher resolve(int index) {
      if(index < matchers.length) {
         return matchers[index];
      }
      throw new IllegalArgumentException("Grammar for " + index + " not found");
   }
   
   public void cache(int index, GrammarMatcher matcher) {
      if(index >= matchers.length) {
         GrammarMatcher[] copy = new GrammarMatcher[index * 2];
         
         for(int i = 0; i < matchers.length; i++) {
            copy[i] = matchers[i];
         }
         matchers = copy;
      }
      matchers[index] = matcher;
   }
}