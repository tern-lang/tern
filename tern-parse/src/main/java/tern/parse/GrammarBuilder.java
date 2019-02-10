package tern.parse;

import java.util.ArrayList;
import java.util.List;

public class GrammarBuilder {

   private final GrammarResolver resolver;
   private final GrammarIndexer indexer;

   public GrammarBuilder(GrammarResolver resolver, GrammarIndexer indexer) {
      this.resolver = resolver;
      this.indexer = indexer;      
   }
   
   public Grammar createSpace(String text, String origin) { 
      return new SpaceGrammar();
   }
   
   public Grammar createLiteral(String text, String origin) { 
      String value = indexer.literal(text);
      return new LiteralGrammar(value);
   }
   
   public Grammar createReference(String text, String origin) {
      int index = indexer.index(text);
      return new ReferenceGrammar(resolver, text, index);
   }   
   
   public Grammar createOptional(Grammar node, String origin) {
      return new OptionalGrammar(node);
   }          
   
   public Grammar createRepeat(Grammar node, String origin) {
      return new RepeatGrammar(node);      
   }
   
   public Grammar createRepeatOnce(Grammar node, String origin) {
      return new RepeatGrammar(node, true);
   }
   
   public Grammar createSpecial(String text, String origin) {
      Symbol[] symbols = Symbol.values();
      
      for(Symbol symbol : symbols) {
         if(symbol.name.equals(text)) {     
            int index = indexer.index(text);
            return new SymbolGrammar(symbol, text, index); 
         }       
      }
      return null;
   }    
   
   public Grammar createMatchBest(List<Grammar> nodes, String origin) {
      Grammar top = nodes.get(0);      
      int count = nodes.size();
      int index = indexer.index(origin);
      
      if(count > 1) {
         List<Grammar> copy = new ArrayList<Grammar>(nodes);         
         MatchOneGrammar choice = new MatchOneGrammar(copy, origin, count, index);  
         
         return choice;
      }
      return top;      
   } 
   
   public Grammar createMatchFirst(List<Grammar> nodes, String origin) {
      Grammar top = nodes.get(0);      
      int count = nodes.size();
      int index = indexer.index(origin);
      
      if(count > 1) {
         List<Grammar> copy = new ArrayList<Grammar>(nodes);         
         MatchFirstGrammar choice = new MatchFirstGrammar(copy, origin, count, index);  
         
         return choice;
      }
      return top;      
   }   
   
   public Grammar createMatchAll(List<Grammar> nodes, String origin) {
      Grammar top = nodes.get(0);      
      int count = nodes.size();
      int index = indexer.index(origin);
      
      if(count > 1) {
         List<Grammar> copy = new ArrayList<Grammar>(nodes);
         MatchAllGrammar group = new MatchAllGrammar(copy, origin, count, index);
         
         return group;
      }
      return top;      
   }
}