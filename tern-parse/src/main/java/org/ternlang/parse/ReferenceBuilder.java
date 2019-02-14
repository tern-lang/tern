package org.ternlang.parse;

import java.util.concurrent.atomic.AtomicReference;

public class ReferenceBuilder {

   private final GrammarResolver resolver; 
   private final String name;
   private final int index;
   
   public ReferenceBuilder(GrammarResolver resolver, String name, int index) {
      this.resolver = resolver;
      this.index = index;
      this.name = name;
   }   
   
   public GrammarMatcher create(GrammarCache cache, int length) {
      Grammar grammar = resolver.resolve(name);
      
      if(grammar == null) {
         throw new ParseException("Grammar '" + name + "' not found");
      }
      return new ReferenceMatcher(cache, grammar, name, index, length);
   }  
   
   private static class ReferenceResolver {
      
      private final AtomicReference<GrammarMatcher> reference;
      private final GrammarCache cache;
      private final Grammar grammar;
      private final int length;
      
      public ReferenceResolver(GrammarCache cache, Grammar grammar, int length) {
         this.reference = new AtomicReference<GrammarMatcher>();
         this.grammar = grammar;
         this.length = length;
         this.cache = cache;;
      }  
      
      public GrammarMatcher resolve() {  
         GrammarMatcher matcher = reference.get();
         
         if(matcher == null) {
            matcher = grammar.create(cache, length);
            reference.set(matcher);
         }
         return matcher;
      }
   }
   
   private static class ReferenceMatcher implements GrammarMatcher {
      
      private final ReferenceResolver resolver;
      private final String name;
      private final int index;
      
      public ReferenceMatcher(GrammarCache cache, Grammar grammar, String name, int index, int length) {
         this.resolver = new ReferenceResolver(cache, grammar, length);
         this.index = index;
         this.name = name;
      }  
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {  
         GrammarMatcher matcher = resolver.resolve();
         int mark = checker.mark(index);   
   
         if(mark != -1) {
            if(matcher.check(checker, 0)) {
               checker.commit(mark, index);
               return true;
            }
            checker.reset(mark, index);
         }
         return false;
      }
   
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {  
         GrammarMatcher matcher = resolver.resolve();
         SyntaxBuilder child = builder.mark(index);   
   
         if(child != null) {
            if(matcher.build(child, 0)) {
               child.commit();
               return true;
            }
            child.reset();
         }
         return false;
      }
      
      @Override
      public String toString() {
         return String.format("<%s>", name);
      }  
   }
}