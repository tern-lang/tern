package org.ternlang.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GrammarCompiler {

   private final GrammarBuilder builder;

   public GrammarCompiler(GrammarResolver resolver, GrammarIndexer indexer) {
      this.builder = new GrammarBuilder(resolver, indexer);
   }   
   
   public Grammar process(String name, String syntax) { 
      RuleIterator iterator = new RuleParser(name, syntax);
      
      if(!iterator.hasNext()) {
         throw new ParseException("Grammar contains no rules");
      }        
      Grammar result = sequence(iterator, RuleType.OPEN_GROUP);
      
      if(result == null) {
         throw new ParseException("Could not consume node for " + name);
      }
      return result;
   }  
   
   private Grammar optional(RuleIterator iterator, RuleType previous) {
      Rule start = iterator.next();
      RuleType open = start.getType();
      
      if(!open.isOptional()) {
         throw new ParseException("Optional does not begin with " + open);
      } 
      Rule rule = iterator.peek();
      RuleType type = rule.getType();
      String origin = rule.getOrigin();
      
      if(type.isToken()) {
         Grammar node = next(iterator, previous);
         
         iterator.next();
         return builder.createOptional(node, origin);         
      } 
      if(type.isOpenGroup() || type.isOpenChoice()) {
         Grammar node = consume(iterator, type);
      
         if(node == null) {
            throw new ParseException("Could not create optional with type " + type);
         }
         return builder.createOptional(node, origin);
      }
      throw new ParseException("Unable to create optional with " + type);      
   }   
   
   private Grammar repeat(RuleIterator iterator, RuleType previous) {
      Rule start = iterator.next();
      RuleType open = start.getType();
      
      if(!open.isRepeat() && !open.isRepeatOnce()) {
         throw new ParseException("Repeat does not begin with " + open);
      } 
      Rule rule = iterator.peek();
      RuleType type = rule.getType();
      String origin = rule.getOrigin();

      if(type.isToken()) {
         Grammar node = next(iterator, previous);         
         
         iterator.next();
         
         if(open.isRepeatOnce()) {
            return builder.createRepeatOnce(node, origin);
         }
         return builder.createRepeat(node, origin);
      } 
      if(type.isOpenGroup() || type.isOpenChoice()) {
         Grammar node = consume(iterator, type);
      
         if(node == null) {
            throw new ParseException("Could not create repeat with type " + type);
         }
         if(open.isRepeatOnce()) {
            return builder.createRepeatOnce(node, origin);
         }
         return builder.createRepeat(node, origin);
      }
      throw new ParseException("Unable to create repeat with " + type);      
   }   
   
   private Grammar group(RuleIterator iterator, RuleType previous) {      
      List<Grammar> nodes = new ArrayList<Grammar>();
      
      if(!iterator.hasNext()) {
         throw new ParseException("Rules have been exhausted");
      }
      Rule start = iterator.next();
      RuleType open = start.getType();
      
      if(!open.isOpenGroup() && !open.isOpenChoice()) {
         throw new ParseException("Group does not begin with " + open);
      }         
      while(iterator.hasNext()) {
         Rule rule = iterator.peek();
         RuleType type = rule.getType();
         String origin = rule.getOrigin();
         
         if(type.isCloseGroup() || type.isCloseChoice()) {
            Grammar group = builder.createMatchAll(nodes, origin);
            
            iterator.next();
            return group;
         }
         if(type.isOpenGroup() || type.isOpenChoice()) {
            Grammar result = consume(iterator, type);
         
            if(result == null) {
               throw new ParseException("Could not consume node of type " + type);
            }
            nodes.add(result);
         } else {
            Grammar result = consume(iterator, previous);
            
            if(result == null) {
               throw new ParseException("Could not consume node of type " + type);
            }
            nodes.add(result);
         }
      }
      throw new ParseException("Group did not terminate");         
   }  
   
   private Grammar sequence(RuleIterator iterator, RuleType previous) {
      AtomicReference<String> last = new AtomicReference<String>();
      List<Grammar> choices = new ArrayList<Grammar>();
      List<Grammar> sequence = new ArrayList<Grammar>();      
      
      if(!iterator.hasNext()) {
         throw new ParseException("Rules have been exhausted");
      }
      while(iterator.hasNext()) {
         Rule rule = iterator.peek();
         RuleType type = rule.getType();
         String origin = rule.getOrigin();
         
         if(type.isSplitter()) {
            Grammar group = builder.createMatchAll(sequence, origin);
            
            sequence.clear();
            choices.add(group);
            iterator.next();
         } else if(type.isToken()) {
            Grammar next = next(iterator, previous);
            
            if(next != null) {
               sequence.add(next);
            }
            iterator.next();
         } else if(!type.isCloseGroup() && !type.isCloseChoice()){
            Grammar next = consume(iterator, type);
            
            if(next != null) {
               sequence.add(next);
            }
            last.set(origin);            
         } else {
            break;// closed the group or repeat!!
         }
         last.set(origin);
      }
      String origin = last.get();
      
      if(!sequence.isEmpty()) {
         Grammar group = builder.createMatchAll(sequence, origin);
         
         sequence.clear();
         choices.add(group);
      }
      if(previous.isOpenChoice()) {
         return builder.createMatchFirst(choices, origin);
      }
      return builder.createMatchBest(choices, origin);
   }       

   private Grammar next(RuleIterator iterator, RuleType previous) {
      Rule rule = iterator.peek();
      RuleType type = rule.getType(); 
      String origin = rule.getOrigin();
      String text = rule.getSymbol();
      
      if(type.isSpace()) {
         return builder.createSpace(text, origin);
      }
      if(type.isLiteral()){         
         return builder.createLiteral(text, origin);
      } 
      if(type.isReference()) {
         return builder.createReference(text, origin);
      }
      if(type.isSpecial()) {
         return builder.createSpecial(text, origin);
      }        
      return null;
   }
   
   private Grammar consume(RuleIterator iterator, RuleType previous) {
      Rule rule = iterator.peek();
      RuleType type = rule.getType();      
      
      if(type.isToken()) {
         return sequence(iterator, previous);
      } 
      if(type.isOpenGroup() || type.isOpenChoice()) {
         return group(iterator, type); // new grouping         
      }
      if(type.isOptional()) {
         return optional(iterator, previous);          
      }
      if(type.isRepeat() || type.isRepeatOnce()) {
         return repeat(iterator, previous);          
      }        
      return null;
   }   
}