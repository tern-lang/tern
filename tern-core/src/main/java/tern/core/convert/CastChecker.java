package tern.core.convert;

import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.INVALID;
import static tern.core.convert.Score.SIMILAR;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tern.core.ModifierType;
import tern.core.function.ClosureFunctionFinder;
import tern.core.function.Function;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;
import tern.core.type.TypeLoader;

public class CastChecker {

   private final FunctionComparator comparator;
   private final ClosureFunctionFinder finder;
   private final TypeExtractor extractor;
   
   public CastChecker(ConstraintMatcher matcher, TypeExtractor extractor, TypeLoader loader) {
      this.comparator = new FunctionComparator(matcher);
      this.finder = new ClosureFunctionFinder(comparator, extractor, loader);
      this.extractor = extractor;
   }
   
   public Score toType(Type actual, Type constraint) throws Exception {
      if(!actual.equals(constraint)) {
         Set<Type> list = extractor.getTypes(actual);
         
         if(list.isEmpty()) {
            return INVALID;
         }
         if(list.contains(constraint)) { // this is slow
            return SIMILAR;
         }
         int modifiers = actual.getModifiers(); 
         
         if(ModifierType.isFunction(modifiers)) {
            return toFunction(constraint, actual);
         }
         return INVALID;
      }
      return EXACT;
   }
   
   public Score toType(Type actual, Type constraint, Object value) throws Exception {
      if(!actual.equals(constraint)) {
         Set<Type> list = extractor.getTypes(actual);
         
         if(list.isEmpty()) {
            return INVALID;
         }
         if(list.contains(constraint)) { // this is slow
            return SIMILAR;
         }
         int modifiers = actual.getModifiers(); 
         
         if(ModifierType.isFunction(modifiers)) {
            return toFunction(constraint, actual);
         }
         return INVALID;
      }
      return EXACT;
   }
   
   public Score toFunction(Type actual, Type constraint) throws Exception {
      int modifiers = constraint.getModifiers(); 
      
      if(ModifierType.isFunction(modifiers)) {
         Function possible = finder.findFunctional(actual);
         List<Function> functions = constraint.getFunctions();
         Iterator<Function> iterator = functions.iterator();
         Scope scope = actual.getScope();
         
         if(iterator.hasNext()) {
            Function required = iterator.next();
         
            if(possible != null) {         
               return comparator.compare(scope, possible, required);         
            }
         }
      }
      return INVALID;
   }
   
   public Score toFunction(Type actual, Type constraint, Object value) throws Exception {
      Type type = extractor.getType(value);
      int modifiers = type.getModifiers(); 
      
      if(ModifierType.isFunction(modifiers)) {
         Function possible = finder.findFunctional(type);
         List<Function> functions = constraint.getFunctions();
         Iterator<Function> iterator = functions.iterator();
         Scope scope = actual.getScope();
         
         if(iterator.hasNext()) {
            Function required = iterator.next();
            
            if(possible != null) {
               return comparator.compare(scope, possible, required);
            }
         }
      }
      return INVALID;
   }
}